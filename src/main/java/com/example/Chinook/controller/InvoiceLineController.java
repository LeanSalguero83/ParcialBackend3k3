package com.example.Chinook.controller;

import com.example.Chinook.model.InvoiceLine;
import com.example.Chinook.service.InvoiceLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-lines")
public class InvoiceLineController {

    private final InvoiceLineService invoiceLineService;

    @Autowired
    public InvoiceLineController(InvoiceLineService invoiceLineService) {
        this.invoiceLineService = invoiceLineService;
    }

    @GetMapping
    public List<InvoiceLine> getAllInvoiceLines() {
        return invoiceLineService.getAllInvoiceLines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceLine> getInvoiceLineById(@PathVariable Integer id) {
        return invoiceLineService.getInvoiceLineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public InvoiceLine createInvoiceLine(@RequestBody InvoiceLine invoiceLine) {
        return invoiceLineService.createInvoiceLine(invoiceLine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceLine> updateInvoiceLine(@PathVariable Integer id, @RequestBody InvoiceLine invoiceLineDetails) {
        return invoiceLineService.updateInvoiceLine(id, invoiceLineDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Integer id) {
        return invoiceLineService.deleteInvoiceLine(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/invoice/{invoiceId}")
    public List<InvoiceLine> getInvoiceLinesByInvoiceId(@PathVariable Integer invoiceId) {
        return invoiceLineService.getInvoiceLinesByInvoiceId(invoiceId);
    }

    @GetMapping("/track/{trackId}")
    public List<InvoiceLine> getInvoiceLinesByTrackId(@PathVariable Integer trackId) {
        return invoiceLineService.getInvoiceLinesByTrackId(trackId);
    }
}