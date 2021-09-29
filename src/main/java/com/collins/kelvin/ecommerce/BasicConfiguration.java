/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collins.kelvin.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {
     @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .antMatcher("/product")
                .authorizeRequests()
                .anyRequest().hasRole("ADMIN")
                .and()
                .httpBasic();
                
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
          .inMemoryAuthentication()
          .withUser("user").password(passwordEncoder().encode("password")).roles("USER")

          .and()
          .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN")
                .and()
                .withUser("agent").password(passwordEncoder().encode("agent")).roles("AGENT");
    }/*
   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/list").permitAll()
                .antMatchers("/product/add_product").permitAll()
                .antMatchers("/product/**").hasRole("ADMIN")
                .antMatchers("/order/**").hasRole("ADMIN")
                .antMatchers("/customer/**").hasRole("ADMIN")
                .and()
                .httpBasic();

    }*/
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}