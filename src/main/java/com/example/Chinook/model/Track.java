package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Track")
@Data
@EqualsAndHashCode(of = "trackId")
@ToString(exclude = {"album", "genre", "mediaType", "playlistTracks", "invoiceLines"})
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private Integer trackId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MediaTypeId", nullable = false)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GenreId")
    private Genre genre;

    @Column(name = "Composer", length = 220)
    private String composer;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlaylistTrack> playlistTracks = new HashSet<>();

    @OneToMany(mappedBy = "track")
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    // Helper methods for bidirectional relationships
    public void addPlaylistTrack(PlaylistTrack playlistTrack) {
        playlistTracks.add(playlistTrack);
        playlistTrack.setTrack(this);
    }

    public void removePlaylistTrack(PlaylistTrack playlistTrack) {
        playlistTracks.remove(playlistTrack);
        playlistTrack.setTrack(null);
    }

    public void addInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLines.add(invoiceLine);
        invoiceLine.setTrack(this);
    }

    public void removeInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLines.remove(invoiceLine);
        invoiceLine.setTrack(null);
    }
}