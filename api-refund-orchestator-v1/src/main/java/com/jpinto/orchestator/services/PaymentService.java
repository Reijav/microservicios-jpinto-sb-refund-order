package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.payment.PaymentRestClientService;
import com.jpinto.orchestator.client.payment.dto.CreatePaymentRequest;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.payment.dto.ProcessPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRestClientService paymentRestClientService;

    public PaymentResponse create(CreatePaymentRequest request) {
        return paymentRestClientService.create(request);
    }

    public PaymentResponse getById(String id) {
        return paymentRestClientService.getById(id);
    }

    public PaymentResponse cancel(UUID id) {
        return paymentRestClientService.cancel(id.toString());
    }

    public PaymentResponse process(UUID id, ProcessPaymentDto processPaymentDto) {
        return paymentRestClientService.process(id.toString(), processPaymentDto);
    }
}
