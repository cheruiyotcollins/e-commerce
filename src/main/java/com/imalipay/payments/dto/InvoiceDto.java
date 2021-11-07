package com.imalipay.payments.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class InvoiceDto {
  private static final long serialVersionUID = 1L;
  private Long id;
  private BigDecimal amount;
  private ZonedDateTime dueDate;
  private String mobile;
  private String paymentMode;
  private BigDecimal totalRepayment;
  private String transactionId;
  private String status;
  private ZonedDateTime  createdAt;
  private String createdBy;
  private ZonedDateTime  updatedAt;
  private String updatedBy;
  private String paymentReference;
  private String vendingReference;
  private boolean callbackRecieved;
  private String callbackResponse;
  private ZonedDateTime  callbackAt;
  private String failedReason;
    }
