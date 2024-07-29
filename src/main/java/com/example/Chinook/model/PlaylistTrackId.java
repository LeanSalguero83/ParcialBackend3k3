package com.example.Chinook.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackId implements Serializable {

    private Integer playlistId;
    private Integer trackId;
}