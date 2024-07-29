package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Genre")
@Data
@EqualsAndHashCode(of = "genreId")
@ToString(exclude = "tracks")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreId")
    private Integer genreId;

    @Column(name = "Name", length = 120)
    private String name;

    @OneToMany(mappedBy = "genre")
    private Set<Track> tracks = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addTrack(Track track) {
        tracks.add(track);
        track.setGenre(this);
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
        track.setGenre(null);
    }
}