/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.services;

import com.collins.kelvin.ecommerce.model.Customer;
import com.collins.kelvin.ecommerce.model.Order;
import com.collins.kelvin.ecommerce.model.Product;
import com.collins.kelvin.ecommerce.repository.CustomerRepository;
import com.collins.kelvin.ecommerce.repository.OrderRepository;
import com.collins.kelvin.ecommerce.repository.ProductRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
public class OrderServices {

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    ProductRepository productRepository;
            
    @Autowired
    CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    
    
    @Async
    public CompletableFuture<List<Order>> getAllOrders() {
        LOGGER.info("Request to get a list of all existing orders");
        
        final List<Order> order = orderRepository.findAll();
        return CompletableFuture.completedFuture(order);
        
    }
    
    public void save(Order order) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        order.setDate(str.toString());
        if (customerRepository.existsById(order.getId())) {
            String s= order.getProduct_list();
            String[] sArr= s.split(",");
            long sum=0;
            for(int i=0;i<sArr.length;i++){
             Product product= productRepository.findById(new Long(Integer.valueOf(sArr[i]))).get();
            sum+=product.getPrice();
            }
            order.setTotal_price(sum);
            orderRepository.save(order);            
        } else {
            throw new Exception("failed, customer id does not exist");
            
        }
//        Customer customer = customerRepository.findById(id).get();
//        customer.addOrder(order);

    }
    
    public void update(Long id, Order order) throws Exception {
        // TODO Auto-generated method stub
        Order order1 = orderRepository.findById(id).get();
        
        order1 = order;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        order1.setDate(str.toString());
         if (customerRepository.existsById(order.getId())) {
            order1.setOrder_id(id);
            String s= order.getProduct_list();
            String[] sArr= s.split(",");
            long sum=0;
            for(int i=0;i<sArr.length;i++){
             Product product= productRepository.findById(new Long(Integer.valueOf(sArr[i]))).get();
            sum+=product.getPrice();
            }
            order1.setTotal_price(sum);
            orderRepository.save(order1);            
        } else {
            throw new Exception("failed, customer id does not exist");
            
        }
        orderRepository.save(order1);
    }
    
    public Order getOrderById(long id) {
        return orderRepository.findById(id).get();
    }
    
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
    
}
