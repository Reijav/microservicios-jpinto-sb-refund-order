package com.jpinto.payment.domain.exception;

import java.util.UUID;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(UUID id) {
        super(String.format("Payment with id [%s] not found", id));
    }
}
