package com.example.Chinook.repository;

import com.example.Chinook.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Integer> {

    List<MediaType> findByNameContainingIgnoreCase(String name);

    @Query("SELECT mt FROM MediaType mt LEFT JOIN FETCH mt.tracks WHERE mt.mediaTypeId = :mediaTypeId")
    Optional<MediaType> findByIdWithTracks(@Param("mediaTypeId") Integer mediaTypeId);

    @Query("SELECT DISTINCT mt FROM MediaType mt JOIN mt.tracks t JOIN t.album a WHERE a.albumId = :albumId")
    List<MediaType> findByAlbumId(@Param("albumId") Integer albumId);
}