/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.controller;

import com.collins.kelvin.ecommerce.model.Customer;
import com.collins.kelvin.ecommerce.services.CustomerService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KEN19283
 */

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    
    @Autowired
    CustomerService customerService;
    private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);



   
    
        @GetMapping("/list")
    public @ResponseBody CompletableFuture<ResponseEntity> list() {
       
        return customerService.getAllCustomers().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetLoanFailure);

        
    }
    private static Function<Throwable, ResponseEntity<? extends List<Customer>>> handleGetLoanFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

     @PostMapping("/add_customer")
    public Customer addCustomer(@RequestBody Customer customer){
           
            customerService.save(customer);
        return customer;
        
        
    }
        @PutMapping("/update_customer/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customer){
       
        customerService.update(id,customer);
    return customerService.getCustomerById(id);
    }

    @GetMapping("/view/{id}")
    public Customer viewCustomer(@PathVariable long id) {

        Customer customer = customerService.getCustomerById(id);

        return customer;
    }

    @DeleteMapping("/delete_customer/{id}")
    public Customer delete(@PathVariable long id) {

        customerService.deleteCustomer(id);
        return new Customer();

    }

    
}
