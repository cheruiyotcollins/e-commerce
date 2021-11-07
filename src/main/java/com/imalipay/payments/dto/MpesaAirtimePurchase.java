package com.imalipay.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MpesaAirtimePurchase {

    String BusinessShortCode="5002823";
    String Password="MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3";
    String Timestamp="20160216165627";   // to edit
    String TransactionType="CustomerPayBillOnline";
    String Amount="100";  //to edit
    String PartyA="254708374149";  // tp edit
    String PartyB="5002823";
    String PhoneNumber="254708374149";  //to edit
    String CallBackURL="http://localhost:9090/payment/mpesa/response";
    String AccountReference="4343";
    String TransactionDesc="Airtime Purchase";
}
