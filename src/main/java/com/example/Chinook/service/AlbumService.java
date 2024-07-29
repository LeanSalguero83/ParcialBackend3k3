package com.example.Chinook.service;

import com.example.Chinook.model.Album;
import com.example.Chinook.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Integer id) {
        return albumRepository.findById(id);
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> updateAlbum(Integer id, Album albumDetails) {
        return albumRepository.findById(id)
                .map(album -> {
                    album.setTitle(albumDetails.getTitle());
                    album.setArtist(albumDetails.getArtist());
                    return albumRepository.save(album);
                });
    }

    public boolean deleteAlbum(Integer id) {
        return albumRepository.findById(id)
                .map(album -> {
                    albumRepository.delete(album);
                    return true;
                })
                .orElse(false);
    }

    public List<Album> getAlbumsByArtistId(Integer artistId) {
        return albumRepository.findByArtistArtistId(artistId);
    }

    public Optional<Album> getAlbumWithTracks(Integer id) {
        return albumRepository.findByIdWithTracks(id);
    }
}