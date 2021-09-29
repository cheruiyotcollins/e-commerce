/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.services;

import com.collins.kelvin.ecommerce.BasicConfiguration;
import com.collins.kelvin.ecommerce.model.Customer;
import com.collins.kelvin.ecommerce.repository.CustomerRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author KEN19283
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    int strength = 10; // work factor of bcrypt
    BasicConfiguration basicConfiguration =new BasicConfiguration();

   
    
    @Async
    public CompletableFuture<List<Customer>> getAllCustomers() {
        LOGGER.info("Request to get a list of all existing customers");
        final List<Customer> customer = customerRepository.findAll();
        Customer customer1= new Customer();
        return CompletableFuture.completedFuture(customer);

    }

    public void save(Customer customer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        customer.setDate(str.toString());
        String s =basicConfiguration.passwordEncoder().encode(customer.getPassword());
        customer.setPassword(s);
        customerRepository.save(customer);
    }

    public void update(Long id, Customer customer) {
        // TODO Auto-generated method stub
        Customer customer1 = customerRepository.findById(id).get();
        customer1 = customer;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        customer1.setDate(str.toString());
        customerRepository.save(customer1);
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).get();
    }

    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }
}
