package com.imalipay.payments.services;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.URI;
import com.imalipay.payments.dto.*;
import com.imalipay.payments.model.PaymentModel;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.imalipay.payments.repository.PaymentRepository;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    PaymentRepository paymentRepository;

    InvoiceDto invoice = new InvoiceDto();
    PaymentModel paymentModel = new PaymentModel();
    MpesaAirtimePurchase mpesaAirtimePurchase =new MpesaAirtimePurchase();
    MpesaAirtimePurchaseResponse mpesaAirtimePurchaseResponse = new MpesaAirtimePurchaseResponse();

    @Async
    public CompletableFuture<List<PaymentModel>> getAllPayments() {
        LOGGER.info("Request to get a list of all existing payments");

        final List<PaymentModel> payment = paymentRepository.findAll();
        return CompletableFuture.completedFuture(payment);

    }

    public PaymentModel getPaymentById(long id) {

        return paymentRepository.findById(id).get();
    }

    public void invoiceLookUp(OneLongDto invoiceNo) throws Exception {
        LOGGER.info("Preparing payload to send to mpesa");
        mpesaAirtimePurchase.setAmount("20000");
         LOGGER.info("payload "+invoiceNo+" and "+mpesaAirtimePurchase);
//          Long id =invoiceNo.getInvoiceNo();
//        URI uri = null;
//        try {
//            uri = new URI("http://localhost:8080/invoice/"+id+"/details");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        RestTemplate restTemplate = new RestTemplate();
//        InvoiceDto invoice = restTemplate.getForObject(uri, InvoiceDto.class);
//        paymentModel = paymentModelSetup(invoice);
//        paymentProcessing(paymentModel);
 
        URI uri = null;
        try {
            uri = new URI("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate");
            LOGGER.info("*************************************************************Preparing payload to send to mpesa");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        mpesaAirtimePurchaseResponse = restTemplate.postForObject(uri, mpesaAirtimePurchase, MpesaAirtimePurchaseResponse.class);

    }

    public void paymentProcessing(PaymentModel paymentModel) throws Exception {
        LOGGER.info("processing payment");

        if (paymentModel.getInvoice_status().equals("1")) {
            if (paymentModel.getPayment_mode().equalsIgnoreCase("MPESA")) {
                LOGGER.info("Preparing payload to send to cellulant");

//                mpesaAirtimePurchase.setAmount(paymentModel.getAmount().toString());
//                mpesaAirtimePurchase.setBusinessShortCode(paymentModel.getInvoice().toString());
//                mpesaAirtimePurchase.setMsisdn(paymentModel.getMobile().toString());
//                mpesaAirtimePurchase.setCommand_ID(paymentModel.getCustomer().toString());
//                mpesaAirtimePurchase.setShortCode(invoice.getVendingReference());
                URI uri = null;
                try {
                    uri = new URI("https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                RestTemplate restTemplate = new RestTemplate();
                mpesaAirtimePurchaseResponse = restTemplate.postForObject(uri, mpesaAirtimePurchase, MpesaAirtimePurchaseResponse.class);
            }

        } else {
            throw new Exception("You want to purchase " + invoice.getAmount() + " amount og airtime which is currently unavailable");
        }
        paymentRepository.save(paymentModel);
    }

    public PaymentModel paymentModelSetup(InvoiceDto invoice) {
        paymentModel.setInvoice(invoice.getId());
        paymentModel.setInvoice_status(invoice.getStatus());
        paymentModel.setFailed_reason(invoice.getFailedReason());
        paymentModel.setTransactio_reference(invoice.getPaymentReference());
        paymentModel.setAmount(invoice.getAmount());
        paymentModel.setPayment_date(invoice.getCreatedAt());
        paymentModel.setMobile(invoice.getMobile());
        paymentModel.setPayment_mode(invoice.getPaymentMode());
        return paymentModel;
    }

    public void productCallBack(MpesaAirtimePurchaseResponse mpesaAirtimePurchaseResponse) {
        URI uri = null;
        try {
            uri = new URI("http://localhost:8080/product/payment/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, mpesaAirtimePurchaseResponse, MpesaAirtimePurchaseResponse.class);
    }

    public void update(long id, PaymentModel paymentModelUpdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate str = LocalDate.now();
        str.format(formatter);
        PaymentModel payment = paymentRepository.findById(id).get();
        payment = paymentModel;
        paymentRepository.save(payment);

    }

    public void deletePayment(long id) {
        paymentRepository.deleteById(id);
    }

}
