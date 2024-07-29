package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Artist")
@Data
@EqualsAndHashCode(of = "artistId")
@ToString(exclude = "albums")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Integer artistId;

    @Column(name = "Name", length = 120)
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addAlbum(Album album) {
        albums.add(album);
        album.setArtist(this);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setArtist(null);
    }
}