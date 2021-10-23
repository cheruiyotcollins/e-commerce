/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
     @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                //Customer
                .antMatchers(HttpMethod.GET, "/customer/**").hasAnyRole("AGENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/customer/view/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/customer/add_customer").hasAnyRole("ADMIN","AGENT")
                .antMatchers(HttpMethod.PUT, "/customer/**").hasAnyRole("ADMIN","AGENT")
                .antMatchers(HttpMethod.DELETE, "/customer/**").hasRole("ADMIN")
                 //Order
                .antMatchers(HttpMethod.GET, "/order/**").hasAnyRole("ADMIN","AGENT")
                .antMatchers(HttpMethod.GET, "/order/view/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/order/add_order").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/order/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/order/**").hasRole("ADMIN")
                 //Product
                //.antMatchers(HttpMethod.GET,"/product/**").permitAll()
                .antMatchers(HttpMethod.GET,"/product/**").hasAnyRole("ADMIN","USER","AGENT")
                .antMatchers(HttpMethod.POST, "/product/add_product").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/product/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN").and()
                .csrf().disable()
                .formLogin().disable();
                
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
          .inMemoryAuthentication()
          .withUser("user").password(passwordEncoder().encode("user")).roles("USER")

          .and()
          .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN")
                .and()
                .withUser("agent").password(passwordEncoder().encode("agent")).roles("AGENT");
    }
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}