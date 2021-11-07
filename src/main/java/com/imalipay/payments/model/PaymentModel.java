
package com.imalipay.payments.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;

/**
 *
 * @author KEN19283
 */
@Entity
@Data
@Table(name="payment")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PaymentModel implements Serializable{
   
  private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "invoice")
    private Long invoice;

    @Column(name = "customer")
    private String customer;

    @Column(name = "transaction_reference")
    private String transactio_reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "payment_date")
    private ZonedDateTime payment_date;

    @Column(name = "payment_mode")
    private String payment_mode;

     @Column(name = "invoice_status")
    private String invoice_status;

    @Column(name = "failed_reason", nullable = true)
    private String failed_reason;
    
    
}
