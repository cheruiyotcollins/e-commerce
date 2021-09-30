/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author KEN19283
 */
@Entity
@Data
@Table(name="product")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long product_id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="manufacturer")
    private String manufacturer;
    @Column(name="category")
    private String category;


    @Column(name="description")
    private String description;
    
    @Column(name="stock")
    private Long stock;
    
    @Column(name="quantity")
    private int quantity;
    
    @Column(name="price")
    private Long price;
    
    @Column(name="date")
    private String date;
    

    
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order; 
    
    
}
