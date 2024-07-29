package com.example.Chinook.controller;

import com.example.Chinook.model.Artist;
import com.example.Chinook.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Integer id) {
        return artistService.getArtistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Integer id, @RequestBody Artist artistDetails) {
        return artistService.updateArtist(id, artistDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Integer id) {
        return artistService.deleteArtist(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> artistExists(@PathVariable Integer id) {
        boolean exists = artistService.artistExists(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<Artist> getArtistWithAlbums(@PathVariable Integer id) {
        return artistService.getArtistWithAlbums(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}