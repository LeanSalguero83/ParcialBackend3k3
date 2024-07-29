package com.example.Chinook.repository;

import com.example.Chinook.dto.TrackArtistGenreDTO;
import com.example.Chinook.dto.TrackCustomerArtistDTO;
import com.example.Chinook.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {




    @Query(value = "SELECT t.* FROM Track t " +
            "JOIN Album a ON t.AlbumId = a.AlbumId " +
            "JOIN Artist ar ON a.ArtistId = ar.ArtistId " +
            "WHERE ar.ArtistId = :artistId " +
            "AND t.GenreId = :genreId " +
            "ORDER BY RAND() " +
            "LIMIT :limit",
            nativeQuery = true)
    List<Track> findRandomTracksByArtistAndGenre(@Param("artistId") Integer artistId,
                                                 @Param("genreId") Integer genreId,
                                                 @Param("limit") Integer limit);

    @Query("SELECT t FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "WHERE c.customerId = :customerId " +
            "AND LOWER(t.composer) LIKE LOWER(CONCAT('%', :composerFilter, '%'))")
    List<Track> findTracksByCustomerAndComposer(@Param("customerId") Integer customerId,
                                                @Param("composerFilter") String composerFilter);


    @Query("SELECT NEW com.example.Chinook.dto.TrackArtistGenreDTO(t.trackId, t.name, a.title, ar.name, t.milliseconds) " +
            "FROM Track t " +
            "JOIN t.album a " +
            "JOIN a.artist ar " +
            "WHERE ar.artistId = :artistId AND t.genre.genreId = :genreId")
    List<TrackArtistGenreDTO> findTracksByArtistAndGenre(@Param("artistId") Integer artistId,
                                                         @Param("genreId") Integer genreId);


    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.customerId = :customerId")
    boolean existsById(@Param("customerId") Integer customerId);

    @Query("SELECT DISTINCT t FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "WHERE c.customerId = :customerId")
    List<Track> findTracksByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT DISTINCT t FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "WHERE c.customerId = :customerId " +
            "AND t.genre.genreId = :genreId")
    List<Track> findTracksByCustomerAndGenre(@Param("customerId") Integer customerId,
                                             @Param("genreId") Integer genreId);

    @Query("SELECT NEW com.example.Chinook.dto.TrackCustomerArtistDTO(t.trackId, t.name, t.milliseconds, t.unitPrice) " +
            "FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "JOIN t.album a " +
            "JOIN a.artist ar " +
            "WHERE c.customerId = :customerId " +
            "AND ar.artistId = :artistId")
    List<TrackCustomerArtistDTO> findTracksByCustomerAndArtist(@Param("customerId") Integer customerId,
                                                               @Param("artistId") Integer artistId);


    @Query(value = "SELECT t.* FROM Track t " +
            "JOIN Album a ON t.AlbumId = a.AlbumId " +
            "JOIN Artist ar ON a.ArtistId = ar.ArtistId " +
            "WHERE ar.ArtistId = :artistId " +
            "AND t.GenreId = :genreId " +
            "ORDER BY t.UnitPrice DESC, RAND()",
            nativeQuery = true)
    List<Track> findTracksByArtistAndGenreOrderedByUnitPriceDesc(
            @Param("artistId") Integer artistId,
            @Param("genreId") Integer genreId);

    @Query("SELECT DISTINCT t FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "WHERE c.customerId = :customerId " +
            "AND LOWER(t.composer) LIKE LOWER(CONCAT('%', :composerFilter, '%')) " +
            "ORDER BY RAND()")
    List<Track> findRandomTracksByCustomerAndComposerFilter(
            @Param("customerId") Integer customerId,
            @Param("composerFilter") String composerFilter);

    @Query("SELECT t FROM Track t " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "JOIN i.customer c " +
            "WHERE c.customerId = :customerId " +
            "AND t.genre.genreId = :genreId " +
            "AND LOWER(t.composer) LIKE LOWER(CONCAT('%', :composerFilter, '%')) " +
            "ORDER BY RAND()")
    List<Track> findTracksForCustomPlaylist(
            @Param("customerId") Integer customerId,
            @Param("genreId") Integer genreId,
            @Param("composerFilter") String composerFilter);

    @Query("SELECT t FROM Track t " +
            "JOIN t.album a " +
            "JOIN a.artist ar " +
            "JOIN t.invoiceLines il " +
            "JOIN il.invoice i " +
            "WHERE i.customer.customerId = :customerId AND ar.artistId = :artistId " +
            "ORDER BY a.albumId, t.trackId")
    List<Track> findTracksByCustomerAndArtistOrderedByAlbum(@Param("customerId") Integer customerId,
                                                            @Param("artistId") Integer artistId);



}