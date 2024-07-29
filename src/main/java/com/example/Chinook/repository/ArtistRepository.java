package com.example.Chinook.repository;

import com.example.Chinook.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {


    @Query("SELECT a FROM Artist a LEFT JOIN FETCH a.albums WHERE a.artistId = :artistId")
    Optional<Artist> findByIdWithAlbums(@Param("artistId") Integer artistId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Artist a WHERE a.artistId = :artistId")
    boolean existsById(@Param("artistId") Integer artistId);
}