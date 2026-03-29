package com.jpinto.orchestator.services.sagaapprove;

public interface SagaApproveStep {
    void execute(ApproveOrderRefundSagaContext context);

    void compensate(ApproveOrderRefundSagaContext context);
}
