package com.example.Chinook.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomPlaylistResponse {
    private String name;
    private Long totalDurationInSeconds;
    private List<CustomTrackInfo> tracks;

    @Data
    public static class CustomTrackInfo {
        private String name;
        private Integer durationInSeconds;
        private BigDecimal unitPrice;
    }
}