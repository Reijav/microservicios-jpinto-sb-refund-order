package com.jpinto.orchestator.services.sagapayment;

import com.jpinto.orchestator.client.payment.dto.ProcessPaymentDto;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Order(300)
@Slf4j
@Component
@RequiredArgsConstructor
public class PayRefundStep implements SagaPaymentStep{
    private final PaymentService paymentService;
    private final OrderRefundService orderRefundService;

    @Override
    public void execute(PaymentSagaContext context) {
        log.info("Paying the order refund on api payment service.");
        paymentService.process(UUID.fromString(context.getPayPaymentRequest().getPaymentId()), ProcessPaymentDto.builder().transactionId(context.getTransactionDto().getIdTransaction()).build());
    }

    @Override
    public void compensate(PaymentSagaContext context) {
        log.info("Compensating paying the order refund on api payment service.");
        paymentService.cancel(UUID.fromString(context.getPayPaymentRequest().getPaymentId()));
    }
}
