package com.example.Chinook.repository;

import com.example.Chinook.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.invoiceLines WHERE i.invoiceId = :invoiceId")
    Optional<Invoice> findByIdWithInvoiceLines(@Param("invoiceId") Integer invoiceId);

    List<Invoice> findByCustomerCustomerId(Integer customerId);
}