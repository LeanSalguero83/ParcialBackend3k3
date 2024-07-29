package com.example.Chinook.service;

import com.example.Chinook.dto.*;
import com.example.Chinook.exception.NoContentException;
import com.example.Chinook.model.Playlist;
import com.example.Chinook.model.PlaylistTrack;
import com.example.Chinook.model.Track;
import com.example.Chinook.repository.PlaylistRepository;
import com.example.Chinook.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final TrackRepository trackRepository;
    private final ArtistService artistService;
    private final GenreService genreService;

    private final CustomerService customerService;


    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, TrackRepository trackRepository,
                           ArtistService artistService, GenreService genreService, CustomerService customerService) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.artistService = artistService;
        this.genreService = genreService;
        this.customerService = customerService;
    }


    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Integer id) {
        return playlistRepository.findById(id);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Optional<Playlist> updatePlaylist(Integer id, Playlist playlistDetails) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlist.setName(playlistDetails.getName());
                    return playlistRepository.save(playlist);
                });
    }

    public boolean deletePlaylist(Integer id) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlistRepository.delete(playlist);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Playlist> getPlaylistWithTracks(Integer id) {
        return playlistRepository.findByIdWithTracks(id);
    }

    public Long calculatePlaylistDuration(Integer id) {
        return playlistRepository.calculateTotalDuration(id);
    }

    public Double calculatePlaylistCost(Integer id) {
        return playlistRepository.calculateTotalCost(id);
    }


    public Playlist createPlaylistFromRequest(PlaylistCreationRequestDTO request) {
        if (!artistService.artistExists(request.getArtistId())) {
            throw new EntityNotFoundException("Artist not found");
        }

        if (!genreService.genreExists(request.getGenreId())) {
            throw new EntityNotFoundException("Genre not found");
        }

        List<Track> tracks = trackRepository.findTracksByArtistAndGenreOrderedByUnitPriceDesc(
                request.getArtistId(), request.getGenreId());

        if (tracks.isEmpty()) {
            throw new NoContentException("No tracks found for the specified artist and genre");
        }

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());

        long totalDuration = 0;
        long requestedDurationInMillis = request.getDurationInMinutes() * 60L * 1000L;

        for (Track track : tracks) {
            if (totalDuration + track.getMilliseconds() <= requestedDurationInMillis) {
                PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);
                playlist.addPlaylistTrack(playlistTrack);
                totalDuration += track.getMilliseconds();
            } else {
                break;
            }
        }

        return playlistRepository.save(playlist);
    }

    public PlaylistResponseDTO createPlaylistByComposer(PlaylistCreationByComposerDTO request) {
        if (!customerService.customerExists(request.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found");
        }

        List<Track> tracks = trackRepository.findRandomTracksByCustomerAndComposerFilter(
                request.getCustomerId(), request.getComposerFilter());

        if (tracks.isEmpty()) {
            throw new NoContentException("No tracks found for the specified criteria");
        }

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());

        List<PlaylistTrack> playlistTracks = new ArrayList<>();
        int totalDuration = 0;

        for (Track track : tracks) {
            if (totalDuration + track.getMilliseconds() <= request.getMaxDurationInSeconds() * 1000) {
                PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);
                playlistTracks.add(playlistTrack);
                totalDuration += track.getMilliseconds();
            } else {
                break;
            }
        }

        playlist.setPlaylistTracks(new HashSet<>(playlistTracks));

        playlistRepository.save(playlist);

        return convertToPlaylistResponseDTO(playlist);
    }

    private PlaylistResponseDTO convertToPlaylistResponseDTO(Playlist playlist) {
        PlaylistResponseDTO responseDTO = new PlaylistResponseDTO();
        responseDTO.setName(playlist.getName());
        responseDTO.setTotalDurationInSeconds(playlist.getPlaylistTracks().stream()
                .mapToLong(pt -> pt.getTrack().getMilliseconds() / 1000)
                .sum());

        responseDTO.setTracks(playlist.getPlaylistTracks().stream()
                .map(this::convertToTrackDTO)
                .sorted(Comparator.comparing(PlaylistResponseDTO.TrackDTO::getUnitPrice).reversed())
                .collect(Collectors.toList()));

        return responseDTO;
    }

    private PlaylistResponseDTO.TrackDTO convertToTrackDTO(PlaylistTrack playlistTrack) {
        PlaylistResponseDTO.TrackDTO trackDTO = new PlaylistResponseDTO.TrackDTO();
        trackDTO.setName(playlistTrack.getTrack().getName());
        trackDTO.setDurationInSeconds(playlistTrack.getTrack().getMilliseconds() / 1000);
        trackDTO.setUnitPrice(playlistTrack.getTrack().getUnitPrice().doubleValue());
        return trackDTO;
    }

    public PlaylistResponseDTO createCustomPlaylist(CustomPlaylistCreationRequestDTO request) {
        if (!customerService.customerExists(request.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found");
        }

        if (!genreService.genreExists(request.getGenreId())) {
            throw new EntityNotFoundException("Genre not found");
        }

        List<Track> tracks = trackRepository.findTracksForCustomPlaylist(
                request.getCustomerId(),
                request.getGenreId(),
                request.getComposerFilter()
        );

        if (tracks.isEmpty()) {
            throw new NoContentException("No tracks found matching the criteria");
        }

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());

        long totalDuration = 0;
        long maxDurationInMillis = request.getMaxDurationInSeconds() * 1000L;

        for (Track track : tracks) {
            if (totalDuration + track.getMilliseconds() <= maxDurationInMillis) {
                PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);
                playlist.addPlaylistTrack(playlistTrack);
                totalDuration += track.getMilliseconds();
            } else {
                break;
            }
        }

        playlist = playlistRepository.save(playlist);
        return convertToPlaylistResponseDTO(playlist);
    }


    public PlaylistByArtistResponseDTO createPlaylistByArtist(PlaylistCreationByArtistDTO request) {
        if (!customerService.customerExists(request.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found");
        }

        if (!artistService.artistExists(request.getArtistId())) {
            throw new EntityNotFoundException("Artist not found");
        }

        List<Track> tracks = trackRepository.findTracksByCustomerAndArtistOrderedByAlbum(
                request.getCustomerId(), request.getArtistId());

        if (tracks.isEmpty()) {
            throw new NoContentException("No tracks found for the specified customer and artist");
        }

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());

        List<PlaylistByArtistResponseDTO.TrackDTO> responseTracks = new ArrayList<>();
        int totalDuration = 0;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Track track : tracks) {
            if (totalDuration + track.getMilliseconds() <= request.getMaxDurationInSeconds() * 1000) {
                PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);
                playlist.addPlaylistTrack(playlistTrack);

                totalDuration += track.getMilliseconds();
                totalCost = totalCost.add(track.getUnitPrice());

                PlaylistByArtistResponseDTO.TrackDTO trackDTO = new PlaylistByArtistResponseDTO.TrackDTO();
                trackDTO.setName(track.getName());
                trackDTO.setAlbumName(track.getAlbum().getTitle());
                trackDTO.setDurationInSeconds(track.getMilliseconds() / 1000);
                trackDTO.setUnitPrice(track.getUnitPrice());
                responseTracks.add(trackDTO);
            } else {
                break;
            }
        }

        playlistRepository.save(playlist);

        PlaylistByArtistResponseDTO response = new PlaylistByArtistResponseDTO();
        response.setName(playlist.getName());
        response.setTotalDurationInSeconds(totalDuration / 1000);
        response.setTotalCost(totalCost);
        response.setTracks(responseTracks);

        return response;
    }






}