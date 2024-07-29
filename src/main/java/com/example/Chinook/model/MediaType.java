package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MediaType")
@Data
@EqualsAndHashCode(of = "mediaTypeId")
@ToString(exclude = "tracks")
public class MediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MediaTypeId")
    private Integer mediaTypeId;

    @Column(name = "Name", length = 120)
    private String name;

    @OneToMany(mappedBy = "mediaType")
    private Set<Track> tracks = new HashSet<>();

    // Helper methods for bi-directional relationship
    public void addTrack(Track track) {
        tracks.add(track);
        track.setMediaType(this);
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
        track.setMediaType(null);
    }
}