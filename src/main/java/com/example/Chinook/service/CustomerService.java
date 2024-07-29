package com.example.Chinook.service;

import com.example.Chinook.model.Customer;
import com.example.Chinook.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> updateCustomer(Integer id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(customerDetails.getFirstName());
                    customer.setLastName(customerDetails.getLastName());
                    customer.setCompany(customerDetails.getCompany());
                    customer.setAddress(customerDetails.getAddress());
                    customer.setCity(customerDetails.getCity());
                    customer.setState(customerDetails.getState());
                    customer.setCountry(customerDetails.getCountry());
                    customer.setPostalCode(customerDetails.getPostalCode());
                    customer.setPhone(customerDetails.getPhone());
                    customer.setFax(customerDetails.getFax());
                    customer.setEmail(customerDetails.getEmail());
                    customer.setSupportRep(customerDetails.getSupportRep());
                    return customerRepository.save(customer);
                });
    }

    public boolean deleteCustomer(Integer id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return true;
                })
                .orElse(false);
    }

    public Optional<Customer> getCustomerWithInvoices(Integer id) {
        return customerRepository.findByIdWithInvoices(id);
    }

    public boolean customerExists(Integer customerId) {
        return customerRepository.existsById(customerId);
    }
}