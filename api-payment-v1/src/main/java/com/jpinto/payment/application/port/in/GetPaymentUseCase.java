package com.jpinto.payment.application.port.in;

import com.jpinto.payment.application.dto.response.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface GetPaymentUseCase {

    PaymentResponse getPaymentById(UUID id);

    List<PaymentResponse> getAllPayments();
}
