package com.example.Chinook.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PlaylistByArtistResponseDTO {
    private String name;
    private Integer totalDurationInSeconds;
    private BigDecimal totalCost;
    private List<TrackDTO> tracks;

    @Data
    public static class TrackDTO {
        private String name;
        private String albumName;
        private Integer durationInSeconds;
        private BigDecimal unitPrice;
    }
}