package com.jpinto.payment.application.port.in;

import com.jpinto.payment.application.dto.request.CreatePaymentRequest;
import com.jpinto.payment.application.dto.response.PaymentResponse;

public interface CreatePaymentUseCase {

    PaymentResponse createPayment(CreatePaymentRequest request);
}
