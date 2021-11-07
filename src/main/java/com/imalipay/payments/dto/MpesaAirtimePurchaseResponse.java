package com.imalipay.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MpesaAirtimePurchaseResponse {
    String status;
    String description;
    Long invoiceNo;
    BigDecimal airtimeAmount;
    BigDecimal discount;
    String customerId;

}
