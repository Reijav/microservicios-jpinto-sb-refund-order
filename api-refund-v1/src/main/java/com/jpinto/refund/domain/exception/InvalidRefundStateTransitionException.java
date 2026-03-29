package com.jpinto.refund.domain.exception;

import com.jpinto.refund.domain.model.RefundState;

public class InvalidRefundStateTransitionException extends RuntimeException {

    public InvalidRefundStateTransitionException(RefundState current, RefundState target) {
        super(String.format("Cannot transition refund from state [%s] to [%s]", current, target));
    }
}
