package com.jpinto.orchestator.client.payment;

import com.jpinto.orchestator.client.payment.dto.CreatePaymentRequest;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.payment.dto.ProcessPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;


import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentRestClientService {
    private final RestClient paymentRestClient;


    public PaymentResponse create(CreatePaymentRequest request) {
        log.info("Calling create payment.");
        return paymentRestClient.post().uri("/payments").body(request).retrieve().body(PaymentResponse.class);
    }

    public PaymentResponse getById(String id) {
        log.info("Calling get  payment by id.");
        return paymentRestClient.get().uri("/payments/{id}",id).retrieve().body(PaymentResponse.class);
    }


    public PaymentResponse cancel( String id) {
        log.info("Calling cancel payment.");
        return paymentRestClient.put().uri("/payments/{id}/cancel", id).retrieve().body(PaymentResponse.class);
    }



    public PaymentResponse process(String id, ProcessPaymentDto processPaymentDto ) {
        log.info("Calling process payment.");
        return paymentRestClient.put().uri("/payments/{id}/process", id).body(processPaymentDto).retrieve().body(PaymentResponse.class);
    }
}
