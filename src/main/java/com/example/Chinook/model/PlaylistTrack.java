package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PlaylistTrack")
@Data
@EqualsAndHashCode(of = {"playlist", "track"})
public class PlaylistTrack {

    @EmbeddedId
    private PlaylistTrackId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    @JoinColumn(name = "PlaylistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trackId")
    @JoinColumn(name = "TrackId")
    private Track track;

    // Constructor, getters, and setters
    public PlaylistTrack() {
        this.id = new PlaylistTrackId();
    }

    public PlaylistTrack(Playlist playlist, Track track) {
        this.playlist = playlist;
        this.track = track;
        this.id = new PlaylistTrackId(playlist.getPlaylistId(), track.getTrackId());
    }
}