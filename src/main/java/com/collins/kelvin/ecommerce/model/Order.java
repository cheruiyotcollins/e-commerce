/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 * @author KEN19283
 */
@Entity
@Data
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;

    @Column(name = "order_name")
    private String order_name;

    @Column(name = "total_price")
    private long total_price;

    @Column(name = "date")
    private String date;
    @Column(name = "product_list")
    private String product_list;

    @Column(name = "id")
    private Long id;

    @OneToMany(targetEntity=Product.class, cascade=CascadeType.MERGE )
    @JoinColumn(name="cp_fk",referencedColumnName="order_id")
    private List<Product> product;
    
//    @OneToMany(mappedBy="order")
//    private List<Product> product;

    
    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return order_name;
    }

    public void setName(String name) {
        this.order_name = name;
    }

    public long getPrice() {
        return total_price;
    }

    public void setPrice(long price) {
        this.total_price = price;
    }



}
