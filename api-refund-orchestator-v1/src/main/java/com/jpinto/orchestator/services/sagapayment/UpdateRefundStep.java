package com.jpinto.orchestator.services.sagapayment;

import com.jpinto.orchestator.client.refund.dto.MarkAsPayedRequest;
import com.jpinto.orchestator.client.refund.dto.RefundState;
import com.jpinto.orchestator.client.refund.dto.RollbackStateRequest;
import com.jpinto.orchestator.services.OrderRefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(400)
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateRefundStep implements  SagaPaymentStep{
    private final OrderRefundService orderRefundService;

    @Override
    public void execute(PaymentSagaContext context) {
        orderRefundService.markAsPayed(context.getRefundOrderResponse().id());
    }

    @Override
    public void compensate(PaymentSagaContext context) {
        orderRefundService.rollbackState(context.getRefundOrderResponse().id(), RollbackStateRequest.builder().rollbackRefundState(RefundState.CREATED).build());
    }
}

