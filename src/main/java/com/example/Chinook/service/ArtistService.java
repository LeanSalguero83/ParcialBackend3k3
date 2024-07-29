package com.example.Chinook.service;

import com.example.Chinook.model.Artist;
import com.example.Chinook.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(Integer id) {
        return artistRepository.findById(id);
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> updateArtist(Integer id, Artist artistDetails) {
        return artistRepository.findById(id)
                .map(artist -> {
                    artist.setName(artistDetails.getName());
                    return artistRepository.save(artist);
                });
    }

    public boolean deleteArtist(Integer id) {
        return artistRepository.findById(id)
                .map(artist -> {
                    artistRepository.delete(artist);
                    return true;
                })
                .orElse(false);
    }


    public Optional<Artist> getArtistWithAlbums(Integer id) {
        return artistRepository.findByIdWithAlbums(id);
    }

    public boolean artistExists(Integer artistId) {
        return artistRepository.existsById(artistId);
    }
}