package com.example.Chinook.dto;

import lombok.Data;

@Data
public class PlaylistCreationByComposerDTO {
    private String name;
    private Integer customerId;
    private String composerFilter;
    private Integer maxDurationInSeconds;
}