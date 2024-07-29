package com.example.Chinook.controller;

import com.example.Chinook.dto.*;
import com.example.Chinook.exception.NoContentException;
import com.example.Chinook.model.Playlist;
import com.example.Chinook.model.PlaylistTrack;
import com.example.Chinook.service.PlaylistService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer id) {
        return playlistService.getPlaylistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Integer id, @RequestBody Playlist playlistDetails) {
        return playlistService.updatePlaylist(id, playlistDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Integer id) {
        return playlistService.deletePlaylist(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<Playlist> getPlaylistWithTracks(@PathVariable Integer id) {
        return playlistService.getPlaylistWithTracks(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/duration")
    public ResponseEntity<Long> calculatePlaylistDuration(@PathVariable Integer id) {
        Long duration = playlistService.calculatePlaylistDuration(id);
        return ResponseEntity.ok(duration);
    }

    @GetMapping("/{id}/cost")
    public ResponseEntity<Double> calculatePlaylistCost(@PathVariable Integer id) {
        Double cost = playlistService.calculatePlaylistCost(id);
        return ResponseEntity.ok(cost);
    }
    @PostMapping("/create")
    public ResponseEntity<PlaylistResponseDTO> createPlaylistFromRequest(@RequestBody PlaylistCreationRequestDTO request) {
        try {
            Playlist playlist = playlistService.createPlaylistFromRequest(request);
            PlaylistResponseDTO response = convertToPlaylistResponseDTO(playlist);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }



    @PostMapping("/by-composer")
    public ResponseEntity<PlaylistResponseDTO> createPlaylistByComposer(@RequestBody PlaylistCreationByComposerDTO request) {
        try {
            PlaylistResponseDTO response = playlistService.createPlaylistByComposer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/custom")
    public ResponseEntity<PlaylistResponseDTO> createCustomPlaylist(@RequestBody CustomPlaylistCreationRequestDTO request) {
        try {
            PlaylistResponseDTO response = playlistService.createCustomPlaylist(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/by-artist")
    public ResponseEntity<PlaylistByArtistResponseDTO> createPlaylistByArtist(@RequestBody PlaylistCreationByArtistDTO request) {
        try {
            PlaylistByArtistResponseDTO response = playlistService.createPlaylistByArtist(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NoContentException e) {
            return ResponseEntity.noContent().build();
        }
    }

    private PlaylistResponseDTO convertToPlaylistResponseDTO(Playlist playlist) {
        PlaylistResponseDTO responseDTO = new PlaylistResponseDTO();
        responseDTO.setName(playlist.getName());

        long totalDurationInSeconds = playlist.getPlaylistTracks().stream()
                .mapToLong(pt -> pt.getTrack().getMilliseconds() / 1000)
                .sum();
        responseDTO.setTotalDurationInSeconds(totalDurationInSeconds);

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


}