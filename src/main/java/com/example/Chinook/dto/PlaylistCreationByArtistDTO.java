package com.example.Chinook.dto;

import lombok.Data;

@Data
public class PlaylistCreationByArtistDTO {
    private String name;
    private Integer customerId;
    private Integer artistId;
    private Integer maxDurationInSeconds;
}