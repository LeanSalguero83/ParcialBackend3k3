package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Playlist")
@Data
@EqualsAndHashCode(of = "playlistId")
@ToString(exclude = "playlistTracks")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaylistId")
    private Integer playlistId;

    @Column(name = "Name", length = 120)
    private String name;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlaylistTrack> playlistTracks = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addPlaylistTrack(PlaylistTrack playlistTrack) {
        playlistTracks.add(playlistTrack);
        playlistTrack.setPlaylist(this);
    }

    public void removePlaylistTrack(PlaylistTrack playlistTrack) {
        playlistTracks.remove(playlistTrack);
        playlistTrack.setPlaylist(null);
    }
}