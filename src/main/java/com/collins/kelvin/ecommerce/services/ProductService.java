/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.services;

import com.collins.kelvin.ecommerce.model.Customer;
import com.collins.kelvin.ecommerce.model.Product;
import com.collins.kelvin.ecommerce.repository.CustomerRepository;
import com.collins.kelvin.ecommerce.repository.ProductRepository;
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
public class ProductService {
        @Autowired
    ProductRepository productRepository;
        
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

   
    
    @Async
    public CompletableFuture<List<Product>> getAllProduct() {
        LOGGER.info("Request to get a list of all existing products");

        final List<Product> product = productRepository.findAll();
        return CompletableFuture.completedFuture(product);

    }

    public void save(Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        product.setDate(str.toString());
        productRepository.save(product);
    }

    public void update(Long id, Product product) {
        // TODO Auto-generated method stub
        Product product1 = productRepository.findById(id).get();
       long a= product1.getProduct_id();
        product1 = product;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        product1.setDate(str.toString());
        product1.setProduct_id(a);
        productRepository.save(product1);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).get();
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
    
}
