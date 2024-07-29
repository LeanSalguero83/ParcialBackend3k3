package com.example.Chinook.service;

import com.example.Chinook.model.Invoice;
import com.example.Chinook.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Integer id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Optional<Invoice> updateInvoice(Integer id, Invoice invoiceDetails) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setCustomer(invoiceDetails.getCustomer());
                    invoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
                    invoice.setBillingAddress(invoiceDetails.getBillingAddress());
                    invoice.setBillingCity(invoiceDetails.getBillingCity());
                    invoice.setBillingState(invoiceDetails.getBillingState());
                    invoice.setBillingCountry(invoiceDetails.getBillingCountry());
                    invoice.setBillingPostalCode(invoiceDetails.getBillingPostalCode());
                    invoice.setTotal(invoiceDetails.getTotal());
                    return invoiceRepository.save(invoice);
                });
    }

    public boolean deleteInvoice(Integer id) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoiceRepository.delete(invoice);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Invoice> getInvoiceWithInvoiceLines(Integer id) {
        return invoiceRepository.findByIdWithInvoiceLines(id);
    }

    public List<Invoice> getInvoicesByCustomerId(Integer customerId) {
        return invoiceRepository.findByCustomerCustomerId(customerId);
    }
}