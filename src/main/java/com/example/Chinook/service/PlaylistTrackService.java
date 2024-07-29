package com.example.Chinook.service;

import com.example.Chinook.model.PlaylistTrack;
import com.example.Chinook.model.PlaylistTrackId;
import com.example.Chinook.repository.PlaylistTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlaylistTrackService {

    private final PlaylistTrackRepository playlistTrackRepository;

    @Autowired
    public PlaylistTrackService(PlaylistTrackRepository playlistTrackRepository) {
        this.playlistTrackRepository = playlistTrackRepository;
    }

    public List<PlaylistTrack> getAllPlaylistTracks() {
        return playlistTrackRepository.findAll();
    }

    public Optional<PlaylistTrack> getPlaylistTrackById(PlaylistTrackId id) {
        return playlistTrackRepository.findById(id);
    }

    public PlaylistTrack createPlaylistTrack(PlaylistTrack playlistTrack) {
        return playlistTrackRepository.save(playlistTrack);
    }

    public Optional<PlaylistTrack> updatePlaylistTrack(PlaylistTrackId id, PlaylistTrack playlistTrackDetails) {
        return playlistTrackRepository.findById(id)
                .map(playlistTrack -> {
                    playlistTrack.setPlaylist(playlistTrackDetails.getPlaylist());
                    playlistTrack.setTrack(playlistTrackDetails.getTrack());
                    return playlistTrackRepository.save(playlistTrack);
                });
    }

    public boolean deletePlaylistTrack(PlaylistTrackId id) {
        return playlistTrackRepository.findById(id)
                .map(playlistTrack -> {
                    playlistTrackRepository.delete(playlistTrack);
                    return true;
                })
                .orElse(false);
    }

    public List<PlaylistTrack> getPlaylistTracksByPlaylistId(Integer playlistId) {
        return playlistTrackRepository.findByPlaylistPlaylistId(playlistId);
    }

    public List<PlaylistTrack> getPlaylistTracksByTrackId(Integer trackId) {
        return playlistTrackRepository.findByTrackTrackId(trackId);
    }

    public void deletePlaylistTrackByPlaylistIdAndTrackId(Integer playlistId, Integer trackId) {
        playlistTrackRepository.deleteByPlaylistIdAndTrackId(playlistId, trackId);
    }
}