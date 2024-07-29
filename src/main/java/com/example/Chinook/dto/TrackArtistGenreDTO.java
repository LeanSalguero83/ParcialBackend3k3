package com.example.Chinook.dto;

import lombok.Data;

@Data
public class TrackArtistGenreDTO {
    private Integer trackId;
    private String trackName;
    private String albumTitle;
    private String artistName;
    private Integer milliseconds;

    public TrackArtistGenreDTO(Integer trackId, String trackName, String albumTitle, String artistName, Integer milliseconds) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.milliseconds = milliseconds;
    }
}