package com.example.Chinook.service;

import com.example.Chinook.model.InvoiceLine;
import com.example.Chinook.repository.InvoiceLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceLineService {

    private final InvoiceLineRepository invoiceLineRepository;

    @Autowired
    public InvoiceLineService(InvoiceLineRepository invoiceLineRepository) {
        this.invoiceLineRepository = invoiceLineRepository;
    }

    public List<InvoiceLine> getAllInvoiceLines() {
        return invoiceLineRepository.findAll();
    }

    public Optional<InvoiceLine> getInvoiceLineById(Integer id) {
        return invoiceLineRepository.findById(id);
    }

    public InvoiceLine createInvoiceLine(InvoiceLine invoiceLine) {
        return invoiceLineRepository.save(invoiceLine);
    }

    public Optional<InvoiceLine> updateInvoiceLine(Integer id, InvoiceLine invoiceLineDetails) {
        return invoiceLineRepository.findById(id)
                .map(invoiceLine -> {
                    invoiceLine.setInvoice(invoiceLineDetails.getInvoice());
                    invoiceLine.setTrack(invoiceLineDetails.getTrack());
                    invoiceLine.setUnitPrice(invoiceLineDetails.getUnitPrice());
                    invoiceLine.setQuantity(invoiceLineDetails.getQuantity());
                    return invoiceLineRepository.save(invoiceLine);
                });
    }

    public boolean deleteInvoiceLine(Integer id) {
        return invoiceLineRepository.findById(id)
                .map(invoiceLine -> {
                    invoiceLineRepository.delete(invoiceLine);
                    return true;
                })
                .orElse(false);
    }

    public List<InvoiceLine> getInvoiceLinesByInvoiceId(Integer invoiceId) {
        return invoiceLineRepository.findByInvoiceInvoiceId(invoiceId);
    }

    public List<InvoiceLine> getInvoiceLinesByTrackId(Integer trackId) {
        return invoiceLineRepository.findByTrackTrackId(trackId);
    }
}