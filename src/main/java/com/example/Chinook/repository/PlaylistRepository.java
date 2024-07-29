package com.example.Chinook.repository;

import com.example.Chinook.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.playlistTracks pt LEFT JOIN FETCH pt.track WHERE p.playlistId = :playlistId")
    Optional<Playlist> findByIdWithTracks(@Param("playlistId") Integer playlistId);

    @Query("SELECT COALESCE(SUM(t.milliseconds), 0) FROM Playlist p JOIN p.playlistTracks pt JOIN pt.track t WHERE p.playlistId = :playlistId")
    Long calculateTotalDuration(@Param("playlistId") Integer playlistId);

    @Query("SELECT COALESCE(SUM(t.unitPrice), 0) FROM Playlist p JOIN p.playlistTracks pt JOIN pt.track t WHERE p.playlistId = :playlistId")
    Double calculateTotalCost(@Param("playlistId") Integer playlistId);
}