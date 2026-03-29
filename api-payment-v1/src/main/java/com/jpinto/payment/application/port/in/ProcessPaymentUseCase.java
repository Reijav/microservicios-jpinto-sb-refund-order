package com.jpinto.payment.application.port.in;

import com.jpinto.payment.application.dto.response.PaymentResponse;

import java.util.UUID;

public interface ProcessPaymentUseCase {

    PaymentResponse processPayment(UUID paymentId, Long transactionalId);
}
