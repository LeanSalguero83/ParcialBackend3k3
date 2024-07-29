package com.example.Chinook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employee")
@Data
@EqualsAndHashCode(of = "employeeId")
@ToString(exclude = {"reportsTo", "subordinates", "customers"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeId")
    private Integer employeeId;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "FirstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "Title", length = 30)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportsTo")
    private Employee reportsTo;

    @OneToMany(mappedBy = "reportsTo")
    private Set<Employee> subordinates = new HashSet<>();

    @Column(name = "BirthDate")
    private LocalDateTime birthDate;

    @Column(name = "HireDate")
    private LocalDateTime hireDate;

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

    @Column(name = "Email", length = 60)
    private String email;

    @OneToMany(mappedBy = "supportRep")
    private Set<Customer> customers = new HashSet<>();

    // Helper methods for bidirectional relationships
    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
        subordinate.setReportsTo(this);
    }

    public void removeSubordinate(Employee subordinate) {
        subordinates.remove(subordinate);
        subordinate.setReportsTo(null);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
        customer.setSupportRep(this);
    }

    public void removeCustomer(Customer customer) {
        customers.remove(customer);
        customer.setSupportRep(null);
    }
}