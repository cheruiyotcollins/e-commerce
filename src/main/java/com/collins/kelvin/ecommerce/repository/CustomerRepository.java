/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.repository;

import com.collins.kelvin.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author KEN19283
 */
public interface CustomerRepository extends JpaRepository<Customer, Long >{

    @Query("SELECT c FROM Customer c where c.name like :name%")
    List<Customer> searchByName(@Param("name") String name);
}
