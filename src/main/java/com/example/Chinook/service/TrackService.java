package com.example.Chinook.service;

import com.example.Chinook.dto.TrackArtistGenreDTO;
import com.example.Chinook.dto.TrackCustomerArtistDTO;
import com.example.Chinook.dto.TrackCustomerDTO;
import com.example.Chinook.dto.TrackCustomerGenreDTO;
import com.example.Chinook.model.Track;
import com.example.Chinook.repository.ArtistRepository;
import com.example.Chinook.repository.TrackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrackService {

    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Optional<Track> getTrackById(Integer id) {
        return trackRepository.findById(id);
    }

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public Optional<Track> updateTrack(Integer id, Track trackDetails) {
        return trackRepository.findById(id)
                .map(track -> {
                    track.setName(trackDetails.getName());
                    track.setAlbum(trackDetails.getAlbum());
                    track.setMediaType(trackDetails.getMediaType());
                    track.setGenre(trackDetails.getGenre());
                    track.setComposer(trackDetails.getComposer());
                    track.setMilliseconds(trackDetails.getMilliseconds());
                    track.setBytes(trackDetails.getBytes());
                    track.setUnitPrice(trackDetails.getUnitPrice());
                    return trackRepository.save(track);
                });
    }

    public boolean deleteTrack(Integer id) {
        return trackRepository.findById(id)
                .map(track -> {
                    trackRepository.delete(track);
                    return true;
                })
                .orElse(false);
    }




    public List<Track> getRandomTracksByArtistAndGenre(Integer artistId, Integer genreId, Integer limit) {
        return trackRepository.findRandomTracksByArtistAndGenre(artistId, genreId, limit);
    }

    public List<Track> getTracksByCustomerAndComposer(Integer customerId, String composerFilter) {
        return trackRepository.findTracksByCustomerAndComposer(customerId, composerFilter);
    }

    public List<TrackArtistGenreDTO> getTracksByArtistAndGenre(Integer artistId, Integer genreId) {
        if (!artistRepository.existsById(artistId)) {
            throw new EntityNotFoundException("Artist not found with id: " + artistId);
        }

        return trackRepository.findTracksByArtistAndGenre(artistId, genreId);
    }

    public List<TrackCustomerDTO> getTracksByCustomerId(Integer customerId) {
        List<Track> tracks = trackRepository.findTracksByCustomerId(customerId);
        return tracks.stream()
                .map(this::convertToTrackCustomerDTO)
                .collect(Collectors.toList());
    }

    private TrackCustomerDTO convertToTrackCustomerDTO(Track track) {
        return new TrackCustomerDTO(
                track.getTrackId(),
                track.getName(),
                track.getComposer(),
                track.getMilliseconds()
        );
    }

    public List<TrackCustomerGenreDTO> getTracksByCustomerAndGenre(Integer customerId, Integer genreId) {
        List<Track> tracks = trackRepository.findTracksByCustomerAndGenre(customerId, genreId);
        return tracks.stream()
                .map(this::convertToTrackCustomerGenreDTO)
                .collect(Collectors.toList());
    }

    private TrackCustomerGenreDTO convertToTrackCustomerGenreDTO(Track track) {
        return new TrackCustomerGenreDTO(
                track.getTrackId(),
                track.getName(),
                track.getComposer(),
                track.getMilliseconds()
        );
    }

    public List<TrackCustomerArtistDTO> getTracksByCustomerAndArtist(Integer customerId, Integer artistId) {
        return trackRepository.findTracksByCustomerAndArtist(customerId, artistId);
    }

}