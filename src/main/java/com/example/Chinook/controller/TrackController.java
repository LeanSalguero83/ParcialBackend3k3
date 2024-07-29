package com.example.Chinook.controller;

import com.example.Chinook.dto.TrackArtistGenreDTO;
import com.example.Chinook.dto.TrackCustomerArtistDTO;
import com.example.Chinook.dto.TrackCustomerDTO;
import com.example.Chinook.dto.TrackCustomerGenreDTO;
import com.example.Chinook.model.Track;
import com.example.Chinook.service.ArtistService;
import com.example.Chinook.service.CustomerService;
import com.example.Chinook.service.TrackService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;
    private final CustomerService customerService;
    private final ArtistService artistService;

    @Autowired
    public TrackController(TrackService trackService, CustomerService customerService, ArtistService artistService) {
        this.trackService = trackService;
        this.customerService = customerService;
        this.artistService = artistService;
    }

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Integer id) {
        return trackService.getTrackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Track createTrack(@RequestBody Track track) {
        return trackService.createTrack(track);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Integer id, @RequestBody Track trackDetails) {
        return trackService.updateTrack(id, trackDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Integer id) {
        return trackService.deleteTrack(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }


    @GetMapping("/by-artist-and-genre")
    public ResponseEntity<List<TrackArtistGenreDTO>> getTracksByArtistAndGenre(
            @RequestParam Integer artistId,
            @RequestParam Integer genreId) {
        try {
            List<TrackArtistGenreDTO> tracks = trackService.getTracksByArtistAndGenre(artistId, genreId);
            if (tracks.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(tracks);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-customer")
    public ResponseEntity<List<TrackCustomerDTO>> getTracksByCustomerId(@RequestParam Integer customerId) {
        if (!customerService.customerExists(customerId)) {
            return ResponseEntity.notFound().build();
        }

        List<TrackCustomerDTO> tracks = trackService.getTracksByCustomerId(customerId);

        if (tracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/by-customer-and-genre")
    public ResponseEntity<List<TrackCustomerGenreDTO>> getTracksByCustomerAndGenre(
            @RequestParam Integer customerId,
            @RequestParam Integer genreId) {
        if (!customerService.customerExists(customerId)) {
            return ResponseEntity.notFound().build();
        }

        List<TrackCustomerGenreDTO> tracks = trackService.getTracksByCustomerAndGenre(customerId, genreId);

        if (tracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/by-customer-and-artist")
    public ResponseEntity<List<TrackCustomerArtistDTO>> getTracksByCustomerAndArtist(
            @RequestParam Integer customerId,
            @RequestParam Integer artistId) {
        if (!customerService.customerExists(customerId)) {
            return ResponseEntity.notFound().build();
        }

        if (!artistService.artistExists(artistId)) {
            return ResponseEntity.notFound().build();
        }

        List<TrackCustomerArtistDTO> tracks = trackService.getTracksByCustomerAndArtist(customerId, artistId);

        if (tracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tracks);
    }
}