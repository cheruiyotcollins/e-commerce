/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.controller;

import com.collins.kelvin.ecommerce.dto.OneString;
import com.collins.kelvin.ecommerce.model.Customer;
import com.collins.kelvin.ecommerce.model.Product;
import com.collins.kelvin.ecommerce.services.ProductService;
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
@RequestMapping(value="/product", produces= MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    ProductService productService;
            
                private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);



   
    
        @GetMapping("/list")
    public @ResponseBody CompletableFuture<ResponseEntity> list() {
       
        return productService.getAllProduct().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetLoanFailure);

        
    }
    @GetMapping("/category")
    public List<Product>  getByCategory(@RequestBody OneString oneString){
            return productService.getByCategory(oneString);
    }
    private static Function<Throwable, ResponseEntity<? extends List<Customer>>> handleGetLoanFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

     @PostMapping("/add_product")
    public Product addCustomer(@RequestBody Product product){
           
            productService.save(product);
        return product;
        
        
    }
        @PutMapping("update_product/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product){
       
        productService.update(id,product);
    return productService.getProductById(id);
    }

    @GetMapping("view/{id}")
    public Product viewProduct(@PathVariable long id) {

        Product product = productService.getProductById(id);

        return product;
    }

    @DeleteMapping("delete_product/{id}")
    public Product delete(@PathVariable long id) {

        productService.deleteProduct(id);
        return new Product();

    }
    
    
}
