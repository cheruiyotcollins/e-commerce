/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.repository;

import com.collins.kelvin.ecommerce.dto.OrderResponse;
import com.collins.kelvin.ecommerce.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
//      Page<Order> findByCustomerId(Long customerId, Pageable pageable);
//    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);
    // @Query("SELECT c.name, p.manufacturer FROM Customer c JOIN c.Products p")


    @Query("SELECT new com.collins.kelvin.ecommerce.dto.OrderResponse(order_name, order_id) FROM Order")
    public List<OrderResponse> getJoinColumns();

    @Query("SELECT new com.collins.kelvin.ecommerce.dto.OrderResponse(order_name, order_id) FROM Order ")
    public List<OrderResponse> getFew();

    @Query("SELECT o  FROM Order o where o.id=?1")
    public List<Order> getByCustomerId(Long id);
}
