package com.jpinto.orchestator.client.payment;

import com.jpinto.orchestator.client.payment.dto.CreatePaymentRequest;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.payment.dto.ProcessPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;


import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRestClientService {
    private final RestClient paymentRestClient;


    public PaymentResponse create(CreatePaymentRequest request) {
        return paymentRestClient.post().uri("/payments").body(request).retrieve().body(PaymentResponse.class);
    }

    public PaymentResponse getById(String id) {
        return paymentRestClient.get().uri("/payments/{id}",id).retrieve().body(PaymentResponse.class);
    }


    // ─── STATE TRANSITIONS ────────────────────────────────────────────────────

    public PaymentResponse cancel( String id) {
        return paymentRestClient.put().uri("/payments/{id}/cancel", id).retrieve().body(PaymentResponse.class);
    }


    public PaymentResponse process(String id, ProcessPaymentDto processPaymentDto ) {
        return paymentRestClient.put().uri("/payments/{id}/process", id).body(processPaymentDto).retrieve().body(PaymentResponse.class);
    }
}
