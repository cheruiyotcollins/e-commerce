/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.services;

import com.collins.kelvin.ecommerce.SecurityConfiguration;
import com.collins.kelvin.ecommerce.dto.OneString;
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
    int strength = 10; // work factor of bcrypt
    SecurityConfiguration securityConfiguration =new SecurityConfiguration();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);

   //Custom customer repository
    public List<Customer> searchByName(OneString oneString){
        String s= oneString.getItem();
        LOGGER.info("****************** Entering search engine*******************8888");
        return  customerRepository.searchByName(s);

    }
    
    @Async
    public CompletableFuture<List<Customer>> getAllCustomers() {
        LOGGER.info("Request to get a list of all existing customers");
        final List<Customer> customer = customerRepository.findAll();
        return CompletableFuture.completedFuture(customer);

    }

    public void save(Customer customer) {

        LocalDate str = LocalDate.now();
        str.format(formatter);
        customer.setDate(str.toString());
        String s = securityConfiguration.passwordEncoder().encode(customer.getPassword());
        customer.setPassword(s);
        customerRepository.save(customer);
    }

    public void update(Long id, Customer customer) {
        // TODO Auto-generated method stub
        Customer customer1 = customerRepository.findById(id).get();
        customer1 = customer;
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
