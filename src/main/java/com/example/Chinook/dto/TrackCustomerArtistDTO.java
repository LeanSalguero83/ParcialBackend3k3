package com.example.Chinook.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TrackCustomerArtistDTO {
    private Integer trackId;
    private String name;
    private Integer seconds;
    private BigDecimal unitPrice;

    public TrackCustomerArtistDTO(Integer trackId, String name, Integer milliseconds, BigDecimal unitPrice) {
        this.trackId = trackId;
        this.name = name;
        this.seconds = milliseconds / 1000;
        this.unitPrice = unitPrice;
    }
}