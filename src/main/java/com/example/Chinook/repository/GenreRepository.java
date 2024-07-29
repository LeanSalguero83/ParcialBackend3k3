package com.example.Chinook.repository;

import com.example.Chinook.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Genre g WHERE g.genreId = :genreId")
    boolean existsById(@Param("genreId") Integer genreId);

    @Query("SELECT g FROM Genre g LEFT JOIN FETCH g.tracks WHERE g.genreId = :genreId")
    Optional<Genre> findByIdWithTracks(@Param("genreId") Integer genreId);
}