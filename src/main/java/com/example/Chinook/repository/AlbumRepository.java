package com.example.Chinook.repository;

import com.example.Chinook.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    List<Album> findByArtistArtistId(Integer artistId);

    @Query("SELECT a FROM Album a LEFT JOIN FETCH a.tracks WHERE a.albumId = :albumId")
    Optional<Album> findByIdWithTracks(@Param("albumId") Integer albumId);


}