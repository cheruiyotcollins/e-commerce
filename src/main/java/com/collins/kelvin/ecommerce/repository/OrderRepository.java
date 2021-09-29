/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.repository;

import com.collins.kelvin.ecommerce.model.Order;
import java.awt.print.Pageable;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author KEN19283
 */
public interface OrderRepository extends JpaRepository<Order,Long>{
//      Page<Order> findByCustomerId(Long customerId, Pageable pageable);
//    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);
}
