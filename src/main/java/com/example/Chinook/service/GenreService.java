package com.example.Chinook.service;

import com.example.Chinook.model.Genre;
import com.example.Chinook.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Integer id) {
        return genreRepository.findById(id);
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> updateGenre(Integer id, Genre genreDetails) {
        return genreRepository.findById(id)
                .map(genre -> {
                    genre.setName(genreDetails.getName());
                    return genreRepository.save(genre);
                });
    }

    public boolean deleteGenre(Integer id) {
        return genreRepository.findById(id)
                .map(genre -> {
                    genreRepository.delete(genre);
                    return true;
                })
                .orElse(false);
    }

    public boolean genreExists(Integer id) {
        return genreRepository.existsById(id);
    }

    public Optional<Genre> getGenreWithTracks(Integer id) {
        return genreRepository.findByIdWithTracks(id);
    }
}