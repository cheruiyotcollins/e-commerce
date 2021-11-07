package com.imalipay.payments.controller;

import com.imalipay.payments.dto.InvoiceDto;
import com.imalipay.payments.dto.MpesaAirtimePurchase;
import com.imalipay.payments.dto.MpesaAirtimePurchaseResponse;
import com.imalipay.payments.dto.OneLongDto;
import com.imalipay.payments.model.PaymentModel;
import com.imalipay.payments.repository.PaymentRepository;
import com.imalipay.payments.services.PaymentService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/payment", produces= MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;
    MpesaAirtimePurchase mpesaAirtimePurchase= new MpesaAirtimePurchase();

    @PostMapping("/invoice/lookup")
    public void incomingInvoice(@RequestBody OneLongDto invoiceNo) throws Exception {
        LOGGER.info("incoming invoice"+ invoiceNo.getInvoiceNo());
        paymentService.invoiceLookUp(invoiceNo);

    }

    @PostMapping("/mpesa/response")
    public void airtimePurchaseResponse(@RequestBody MpesaAirtimePurchaseResponse mpesaAirtimePurchaseResponse) {
        LOGGER.info("A callback to product module on the status of payment");
        paymentService.productCallBack(mpesaAirtimePurchaseResponse);

    }
    ///Testing
    @PostMapping("/mpesa/responsess")
    public MpesaAirtimePurchase airtimePayload(@RequestBody OneLongDto invoiceNo) {
        LOGGER.info("getting payload");
       return  mpesaAirtimePurchase;

    }

    @GetMapping("/list/payments")
    public @ResponseBody
    CompletableFuture<ResponseEntity> list() {

        return paymentService.getAllPayments().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetPaymentFailure);

    }

    private static Function<Throwable, ResponseEntity<? extends List<PaymentModel>>> handleGetPaymentFailure = throwable -> {
        LOGGER.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
         @PostMapping("/add/payment")
    public PaymentModel addPayment(@RequestBody PaymentModel paymentModel) throws Exception {
        paymentRepository.save(paymentModel);
        return paymentModel;
    }

    @PutMapping("/update/payment/{id}")
    public PaymentModel paymentRequest(@PathVariable long id, @RequestBody PaymentModel paymentModel) throws Exception {

        paymentService.update(id, paymentModel);
        return paymentModel;
    }


    @GetMapping("view/payment/{id}")
    public PaymentModel viewPayment(@PathVariable long id) {

        return paymentService.getPaymentById(id);
    }

    @DeleteMapping("delete/payment/{id}")
    public PaymentModel deletePayment(@PathVariable long id) {

        paymentService.deletePayment(id);
        return new PaymentModel();

    }

    
    
}
