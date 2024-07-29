package com.example.Chinook.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlaylistResponseDTO {
    private String name;
    private Long totalDurationInSeconds;
    private List<TrackDTO> tracks;

    @Data
    public static class TrackDTO {
        private String name;
        private Integer durationInSeconds;
        private Double unitPrice;
    }
}