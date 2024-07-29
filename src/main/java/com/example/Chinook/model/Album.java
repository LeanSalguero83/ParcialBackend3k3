package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Album")
@Data
@EqualsAndHashCode(of = "albumId")
@ToString(exclude = {"artist", "tracks"})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer albumId;

    @Column(name = "Title", nullable = false, length = 160)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ArtistId", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Track> tracks = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addTrack(Track track) {
        tracks.add(track);
        track.setAlbum(this);
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
        track.setAlbum(null);
    }
}