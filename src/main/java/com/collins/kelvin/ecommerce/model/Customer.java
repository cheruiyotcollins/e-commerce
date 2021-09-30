/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 * @author KEN19283
 */
@Entity
@Table(name = "customer")
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Customer implements Serializable{
  

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name; 
   
    @Column(name = "phone")
    private String phone; 
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "location")
    private String location;
    
     @Column(name = "date")
    private String date;
     
//     @OneToMany(mappedBy="customer")
//     private Set<Order> orders = new HashSet<Order>();
//     
//     
//     
////     public void addOrder(Order order) {
////        orders.add(order);
////        order.setCustomer(this);
////    }
////
////    public void removeOrder(Order order) {
////        orders.add(order);
////        order.setCustomer(null);
////    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
   
    
}
