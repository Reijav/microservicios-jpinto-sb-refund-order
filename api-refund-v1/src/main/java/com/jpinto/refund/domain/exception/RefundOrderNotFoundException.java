package com.jpinto.refund.domain.exception;

import java.util.UUID;

public class RefundOrderNotFoundException extends RuntimeException {

    public RefundOrderNotFoundException(UUID id) {
        super(String.format("RefundOrder with id [%s] not found", id));
    }

    public RefundOrderNotFoundException(String paymentId) {
        super(String.format("RefundOrder with payment id [%s] not found", paymentId));
    }
}
