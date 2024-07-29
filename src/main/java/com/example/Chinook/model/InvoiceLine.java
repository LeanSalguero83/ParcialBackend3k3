package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "InvoiceLine")
@Data
@EqualsAndHashCode(of = "invoiceLineId")
@ToString(exclude = {"invoice", "track"})
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceLineId")
    private Integer invoiceLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceId", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrackId", nullable = false)
    private Track track;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}