package com.jpinto.payment.domain.exception;

import com.jpinto.payment.domain.model.PaymentState;

public class InvalidPaymentStateTransitionException extends RuntimeException {

    public InvalidPaymentStateTransitionException(PaymentState current, PaymentState target) {
        super(String.format("Cannot transition payment from state [%s] to [%s]", current, target));
    }
}
