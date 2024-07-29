package com.example.Chinook.repository;

import com.example.Chinook.model.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Integer> {

    List<InvoiceLine> findByInvoiceInvoiceId(Integer invoiceId);

    List<InvoiceLine> findByTrackTrackId(Integer trackId);
}