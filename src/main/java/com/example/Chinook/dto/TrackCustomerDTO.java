package com.example.Chinook.dto;

import lombok.Data;

@Data
public class TrackCustomerDTO {
    private Integer trackId;
    private String name;
    private String composer;
    private Integer seconds;

    public TrackCustomerDTO(Integer trackId, String name, String composer, Integer milliseconds) {
        this.trackId = trackId;
        this.name = name;
        this.composer = composer;
        this.seconds = milliseconds / 1000;
    }
}