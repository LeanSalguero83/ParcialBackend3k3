package com.example.Chinook.controller;

import com.example.Chinook.model.Album;
import com.example.Chinook.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Integer id) {
        return albumService.getAlbumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.createAlbum(album);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Integer id, @RequestBody Album albumDetails) {
        return albumService.updateAlbum(id, albumDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Integer id) {
        return albumService.deleteAlbum(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/artist/{artistId}")
    public List<Album> getAlbumsByArtistId(@PathVariable Integer artistId) {
        return albumService.getAlbumsByArtistId(artistId);
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<Album> getAlbumWithTracks(@PathVariable Integer id) {
        return albumService.getAlbumWithTracks(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}