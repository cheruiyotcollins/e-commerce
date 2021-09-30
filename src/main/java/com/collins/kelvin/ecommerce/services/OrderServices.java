/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.services;

import com.collins.kelvin.ecommerce.dto.OneLong;
import com.collins.kelvin.ecommerce.dto.OrderResponse;
import com.collins.kelvin.ecommerce.model.Order;
import com.collins.kelvin.ecommerce.model.Product;
import com.collins.kelvin.ecommerce.repository.CustomerRepository;
import com.collins.kelvin.ecommerce.repository.OrderRepository;
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
public class OrderServices {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate str;
    long sum;
    String p_list;

    @Async
    public CompletableFuture<List<Order>> getAllOrders() {
        LOGGER.info("Request to get a list of all existing orders");

        final List<Order> order = orderRepository.findAll();
        return CompletableFuture.completedFuture(order);

    }

    public List<Order> getAllOrdersSync() {
        LOGGER.info("Request to get a list of all existing orders");

        final List<Order> order = orderRepository.findAll();
        return order;

    }
    //Custom made Repository methods
     public List<OrderResponse> getJoinList() {
        LOGGER.info("Request to get a list of all existing orders");

         List<OrderResponse> order = orderRepository.getJoinColumns();
        return order;

    }
    public List<OrderResponse> getJoinFew() {
        LOGGER.info("Request to get id column and name");

        List<OrderResponse> order = orderRepository.getFew();
        return order;

    }
    public List<Order> getByCustomerId(OneLong oneLong){
        Long id= oneLong.getNum();
        return orderRepository.getByCustomerId(id);
    }

    //end of custom made

    public void save(Order order) throws Exception {
        str = LocalDate.now();
        str.format(formatter);
        order.setDate(str.toString());
        if (customerRepository.existsById(order.getId())) {
            sum = 0;
            p_list = "";

            for (int i = 0; i < order.getProduct().size(); i++) {
                LOGGER.info("************Calculating totalprice for order given");
                long lo = order.getProduct().get(i).getProduct_id();
                if (productRepository.existsById(lo)) {
                    Product product = productRepository.findById(lo).get();
                    int quantity = order.getProduct().get(i).getQuantity();
                    if (product.getStock() >= quantity) {

                        sum += product.getPrice() * quantity;
                        p_list += lo + ",";
                        product.setStock(product.getStock() - quantity);
                    } else {
                        throw new Exception("You want to purchase " + order.getProduct().get(i).getQuantity() + " units of " + product.getName() + " " + product.getDescription()
                                + " which is Currently less than available stock. Available Stock is: " + product.getStock() + ". Please try a lower value");
                    }
                } else {
                    throw new Exception("One of the Products is missing in the database"+lo);

                }

            }
            order.setProduct_list(p_list);
            order.setTotal_price(sum);
            LOGGER.info("************Saving Order******");
            orderRepository.save(order);
        } else {
            throw new Exception("failed, customer id does not exist");

        }

    }

    public void update(Long id, Order order) throws Exception {
        LOGGER.info("************Updating Order*********");
        Order order1 = orderRepository.findById(id).get();

        order1 = order;

        str = LocalDate.now();
        str.format(formatter);
        order1.setDate(str.toString());
        if (customerRepository.existsById(order.getId())) {
            order1.setOrder_id(id);

            sum = 0;
            p_list = "";

            for (int i = 0; i < order.getProduct().size(); i++) {
                LOGGER.info("************Calculating total price for order given");
                long lo = order.getProduct().get(i).getProduct_id();
                if (productRepository.existsById(lo)) {
                    Product product = productRepository.findById(lo).get();
                    int quantity = order.getProduct().get(i).getQuantity();
                    if (product.getStock() >= quantity) {
                        sum += product.getPrice() * quantity;
                        p_list += lo + ",";
                        product.setStock(product.getStock() - quantity);
                    } else {
                        throw new Exception("You want to purchase " + order.getProduct().get(i).getQuantity() + " units of " + product.getName() + " " + product.getDescription()
                                + " which is Currently less than available stock. Available Stock is: " + product.getStock() + ". Please try a lower value");
                    }
                } else {
                    throw new Exception("One of the Products is missing in the database");

                }

            }
            //Calculating using string of product ids
//            for (int i = 0; i < sArr.length; i++) {
//                Product product = productRepository.findById(new Long(Integer.valueOf(sArr[i]))).get();
//                sum += product.getPrice();
//            }
            order1.setTotal_price(sum);
            LOGGER.info("************finally before saving*********");
            orderRepository.save(order1);
        } else {
            throw new Exception("failed, customer id does not exist");

        }
        LOGGER.info("************Saving Order*********");
        orderRepository.save(order1);
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

}
