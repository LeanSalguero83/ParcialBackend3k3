package com.example.Chinook.controller;

import com.example.Chinook.model.PlaylistTrack;
import com.example.Chinook.model.PlaylistTrackId;
import com.example.Chinook.service.PlaylistTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist-tracks")
public class PlaylistTrackController {

    private final PlaylistTrackService playlistTrackService;

    @Autowired
    public PlaylistTrackController(PlaylistTrackService playlistTrackService) {
        this.playlistTrackService = playlistTrackService;
    }

    @GetMapping
    public List<PlaylistTrack> getAllPlaylistTracks() {
        return playlistTrackService.getAllPlaylistTracks();
    }

    @GetMapping("/{playlistId}/{trackId}")
    public ResponseEntity<PlaylistTrack> getPlaylistTrackById(@PathVariable Integer playlistId, @PathVariable Integer trackId) {
        PlaylistTrackId id = new PlaylistTrackId(playlistId, trackId);
        return playlistTrackService.getPlaylistTrackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PlaylistTrack createPlaylistTrack(@RequestBody PlaylistTrack playlistTrack) {
        return playlistTrackService.createPlaylistTrack(playlistTrack);
    }

    @PutMapping("/{playlistId}/{trackId}")
    public ResponseEntity<PlaylistTrack> updatePlaylistTrack(
            @PathVariable Integer playlistId,
            @PathVariable Integer trackId,
            @RequestBody PlaylistTrack playlistTrackDetails) {
        PlaylistTrackId id = new PlaylistTrackId(playlistId, trackId);
        return playlistTrackService.updatePlaylistTrack(id, playlistTrackDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{playlistId}/{trackId}")
    public ResponseEntity<Void> deletePlaylistTrack(@PathVariable Integer playlistId, @PathVariable Integer trackId) {
        PlaylistTrackId id = new PlaylistTrackId(playlistId, trackId);
        return playlistTrackService.deletePlaylistTrack(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/playlist/{playlistId}")
    public List<PlaylistTrack> getPlaylistTracksByPlaylistId(@PathVariable Integer playlistId) {
        return playlistTrackService.getPlaylistTracksByPlaylistId(playlistId);
    }

    @GetMapping("/track/{trackId}")
    public List<PlaylistTrack> getPlaylistTracksByTrackId(@PathVariable Integer trackId) {
        return playlistTrackService.getPlaylistTracksByTrackId(trackId);
    }

    @DeleteMapping("/playlist/{playlistId}/track/{trackId}")
    public ResponseEntity<Void> deletePlaylistTrackByPlaylistIdAndTrackId(
            @PathVariable Integer playlistId,
            @PathVariable Integer trackId) {
        playlistTrackService.deletePlaylistTrackByPlaylistIdAndTrackId(playlistId, trackId);
        return ResponseEntity.ok().build();
    }
}