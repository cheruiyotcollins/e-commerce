/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.repository;

import com.collins.kelvin.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author KEN19283
 */
public interface ProductRepository extends JpaRepository<Product,Long>{
    
}
