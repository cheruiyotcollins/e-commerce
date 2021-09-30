/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.dto;

import com.collins.kelvin.ecommerce.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author KEN19283
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
   private Order order;
    
}
