/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.controller;

import com.collins.kelvin.ecommerce.dto.OneLong;
import com.collins.kelvin.ecommerce.dto.OneString;
import com.collins.kelvin.ecommerce.dto.OrderRequest;
import com.collins.kelvin.ecommerce.dto.OrderResponse;
import com.collins.kelvin.ecommerce.model.Order;
import com.collins.kelvin.ecommerce.repository.OrderRepository;
import com.collins.kelvin.ecommerce.services.OrderServices;
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

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    OrderServices orderService;
     @Autowired
     OrderRepository orderRepository;
    private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/list")
    public @ResponseBody
    CompletableFuture<ResponseEntity> list() {

        return orderService.getAllOrders().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetOrderFailure);

    }
     @GetMapping("/list1")
    public List<Order> listAsync() {

        return orderService.getAllOrdersSync();

    }
    
    @GetMapping("/joinColumn")
    public List<OrderResponse> joinColumn() {

        return orderService.getJoinList();

    }

    @GetMapping("/joinFew")
    public List<OrderResponse> joinFew() {

        return orderService.getJoinFew();

    }
    @GetMapping("/get_order_by_cust_id")
    public List<Order> getByCustomerId(@RequestBody OneLong oneLong) {

        return orderService.getByCustomerId(oneLong);

    }
    private static Function<Throwable, ResponseEntity<? extends List<Order>>> handleGetOrderFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

    @PostMapping("/add_order/")
    public Order addOrder( @RequestBody Order order) throws Exception {

        orderService.save(order);
        return order;

    }
     @PostMapping("/add_order1/")
    public Order addOrder1( @RequestBody OrderRequest order) throws Exception {
           
        return orderRepository.save(order.getOrder());

    }

    @PutMapping("/update_order/{id}")
    public Order updateOrder(@PathVariable long id, @RequestBody Order order) throws Exception {

        orderService.update(id, order);
        return orderService.getOrderById(id);
    }
    @PutMapping("/update_order_status/{id}")
    public Order updateOrderStatus(@PathVariable long id, @RequestBody OneString oneString) throws Exception {

        orderService.updateOrderStatus(id, oneString);
        return orderService.getOrderById(id);
    }


    @GetMapping("/view/{id}")
    public Order viewOrder(@PathVariable long id) {

        Order order = orderService.getOrderById(id);

        return order;
    }

    @DeleteMapping("/delete_order/{id}")
    public Order delete(@PathVariable long id) {

        orderService.deleteOrder(id);
        return new Order();

    }

}
