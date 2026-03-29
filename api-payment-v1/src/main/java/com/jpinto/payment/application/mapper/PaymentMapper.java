package com.jpinto.payment.application.mapper;

import com.jpinto.payment.application.dto.response.PaymentResponse;
import com.jpinto.payment.domain.model.Payment;

public class PaymentMapper {

    private PaymentMapper() {}

    public static PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getPayeeType(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getTransactionId(),
                payment.getState()
        );
    }
}
