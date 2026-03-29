package com.jpinto.payment.application.port.in;

import com.jpinto.payment.application.dto.response.PaymentResponse;

import java.util.UUID;

public interface CancelPaymentUseCase {

    PaymentResponse cancelPayment(UUID paymentId);
}
