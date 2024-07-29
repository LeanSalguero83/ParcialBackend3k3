package com.example.Chinook.dto;

import lombok.Data;

@Data
public class CustomPlaylistCreationRequestDTO {
    private String name;
    private Integer customerId;
    private Integer genreId;
    private String composerFilter;
    private Integer maxDurationInSeconds;
}