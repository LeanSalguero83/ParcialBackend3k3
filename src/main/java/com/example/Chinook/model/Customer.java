package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customer")
@Data
@EqualsAndHashCode(of = "customerId")
@ToString(exclude = {"supportRep", "invoices"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private Integer customerId;

    @Column(name = "FirstName", nullable = false, length = 40)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "Company", length = 80)
    private String company;

    @Column(name = "Address", length = 70)
    private String address;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "State", length = 40)
    private String state;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Column(name = "Email", nullable = false, length = 60)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupportRepId")
    private Employee supportRep;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invoice> invoices = new HashSet<>();

    // Helper methods for bidirectional relationship
    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setCustomer(this);
    }

    public void removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
        invoice.setCustomer(null);
    }
}