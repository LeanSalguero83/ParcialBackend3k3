package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Invoice")
@Data
@EqualsAndHashCode(of = "invoiceId")
@ToString(exclude = {"customer", "invoiceLines"})
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceId")
    private Integer invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @Column(name = "InvoiceDate", nullable = false)
    private LocalDateTime invoiceDate;

    @Column(name = "BillingAddress", length = 70)
    private String billingAddress;

    @Column(name = "BillingCity", length = 40)
    private String billingCity;

    @Column(name = "BillingState", length = 40)
    private String billingState;

    @Column(name = "BillingCountry", length = 40)
    private String billingCountry;

    @Column(name = "BillingPostalCode", length = 10)
    private String billingPostalCode;

    @Column(name = "Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvoiceLine> invoiceLines = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLines.add(invoiceLine);
        invoiceLine.setInvoice(this);
    }

    public void removeInvoiceLine(InvoiceLine invoiceLine) {
        invoiceLines.remove(invoiceLine);
        invoiceLine.setInvoice(null);
    }
}