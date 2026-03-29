package com.jpinto.orchestator.services.sagapayment;

import com.jpinto.orchestator.services.sagaapprove.ApproveOrderRefundSagaContext;

public interface SagaPaymentStep {
    void execute(PaymentSagaContext context);

    void compensate(PaymentSagaContext context);
}
