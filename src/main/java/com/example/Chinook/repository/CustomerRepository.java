package com.example.Chinook.repository;

import com.example.Chinook.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.customerId = :customerId")
    boolean existsById(@Param("customerId") Integer customerId);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.invoices WHERE c.customerId = :customerId")
    Optional<Customer> findByIdWithInvoices(@Param("customerId") Integer customerId);
}