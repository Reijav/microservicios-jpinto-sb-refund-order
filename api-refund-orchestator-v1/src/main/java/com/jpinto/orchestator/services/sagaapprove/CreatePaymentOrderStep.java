package com.jpinto.orchestator.services.sagaapprove;

import com.jpinto.orchestator.client.payment.dto.CreatePaymentRequest;
import com.jpinto.orchestator.client.payment.dto.PayeeType;
import com.jpinto.orchestator.client.payment.dto.PaymentMethod;
import com.jpinto.orchestator.client.refund.dto.MarkAsPayedRequest;
import com.jpinto.orchestator.client.refund.dto.RefundState;
import com.jpinto.orchestator.client.refund.dto.RollbackStateRequest;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
@Slf4j
@Order(300)
@RequiredArgsConstructor
public class CreatePaymentOrderStep implements  SagaApproveStep{
    private final PaymentService paymentService;
    private final OrderRefundService orderRefundService;

    @Override
    public void execute(ApproveOrderRefundSagaContext context) {
        log.info("Creación de pago ");
        CreatePaymentRequest createPaymentRequest= new CreatePaymentRequest(PayeeType.EMPLOYEE, context.getRefundOrderResponse().totalValue(), PaymentMethod.BANK_TRANSFER, LocalDate.now());
        context.setPaymentResponse( paymentService.create(createPaymentRequest));
        orderRefundService.generatePaymentOrder(context.getRefundOrderResponse().id(),  new MarkAsPayedRequest(context.getPaymentResponse().id().toString()));
    }

    @Override
    public void compensate(ApproveOrderRefundSagaContext context) {
        log.info("Compensación de creación de pago ");
        if(Objects.nonNull(context.getPaymentResponse())){
            paymentService.cancel(context.getPaymentResponse().id());
        }
        orderRefundService.rollbackState(context.getRefundOrderResponse().id(), RollbackStateRequest.builder().rollbackRefundState(RefundState.CREATED).build());
    }
}
