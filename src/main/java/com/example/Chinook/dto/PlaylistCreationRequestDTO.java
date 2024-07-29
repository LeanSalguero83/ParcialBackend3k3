package com.example.Chinook.dto;

import lombok.Data;

@Data
public class PlaylistCreationRequestDTO {
    private String name;
    private Integer artistId;
    private Integer genreId;
    private Integer durationInMinutes;
}