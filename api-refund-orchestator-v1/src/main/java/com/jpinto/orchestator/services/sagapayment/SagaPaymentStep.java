package com.jpinto.orchestator.services.sagapayment;


public interface SagaPaymentStep {
    void execute(PaymentSagaContext context);
    void compensate(PaymentSagaContext context);
}
