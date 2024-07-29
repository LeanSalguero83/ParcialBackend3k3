package com.example.Chinook.repository;

import com.example.Chinook.model.PlaylistTrack;
import com.example.Chinook.model.PlaylistTrackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {

    List<PlaylistTrack> findByPlaylistPlaylistId(Integer playlistId);

    List<PlaylistTrack> findByTrackTrackId(Integer trackId);

    @Modifying
    @Query("DELETE FROM PlaylistTrack pt WHERE pt.playlist.playlistId = :playlistId AND pt.track.trackId = :trackId")
    void deleteByPlaylistIdAndTrackId(@Param("playlistId") Integer playlistId, @Param("trackId") Integer trackId);
}