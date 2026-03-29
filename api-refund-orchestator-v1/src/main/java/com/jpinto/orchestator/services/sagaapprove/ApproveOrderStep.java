package com.jpinto.orchestator.services.sagaapprove;

import com.jpinto.orchestator.client.refund.dto.RefundState;
import com.jpinto.orchestator.client.refund.dto.RollbackStateRequest;
import com.jpinto.orchestator.services.OrderRefundService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(200)
@Slf4j
public class ApproveOrderStep implements SagaApproveStep {
    private final OrderRefundService orderRefundService;

    @Override
    public void execute(ApproveOrderRefundSagaContext context) {
        log.info("Cambio de estado de Orden de reembolso");
        orderRefundService.approve(context.getApproveRefundRequest().orderRefundId(), context.getApproveRefundRequest() );
    }

    @Override
    public void compensate(ApproveOrderRefundSagaContext context) {
        log.info("Compensación cambio de estado de Orden de reembolso");
        orderRefundService.rollbackState(context.getApproveRefundRequest().orderRefundId(), RollbackStateRequest.builder().rollbackRefundState(RefundState.CREATED).build() );
    }
}
