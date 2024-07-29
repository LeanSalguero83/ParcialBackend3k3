package com.example.Chinook.controller;

import com.example.Chinook.model.Genre;
import com.example.Chinook.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        return genreService.getGenreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return genreService.createGenre(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Integer id, @RequestBody Genre genreDetails) {
        return genreService.updateGenre(id, genreDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
        return genreService.deleteGenre(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> genreExists(@PathVariable Integer id) {
        boolean exists = genreService.genreExists(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<Genre> getGenreWithTracks(@PathVariable Integer id) {
        return genreService.getGenreWithTracks(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}