package com.jpinto.orchestator.services.sagapayment;

import com.jpinto.orchestator.client.payment.dto.*;
import com.jpinto.orchestator.client.refund.dto.MarkAsPayedRequest;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        log.info("## PAYMENT ORDER ## 3. Paying the order refund on api payment service.");
        paymentService.process(UUID.fromString(context.getPayPaymentRequest().getPaymentId()), ProcessPaymentDto.builder().transactionId(context.getTransactionDto().getIdTransaction()).build());
    }

    @Override
    public void compensate(PaymentSagaContext context) {
        log.info("## PAYMENT ORDER ## 3. (Compensación) paying the order refund on api payment service.");
        paymentService.cancel(UUID.fromString(context.getPayPaymentRequest().getPaymentId()));

        CreatePaymentRequest createPaymentRequest= new CreatePaymentRequest(PayeeType.EMPLOYEE, context.getRefundOrderResponse().totalValue(), PaymentMethod.BANK_TRANSFER, LocalDate.now(),
                context.getEmpleado().getBank(),
                context.getEmpleado().getAccountNumber());
        context.setPaymentResponse( paymentService.create(createPaymentRequest));
        orderRefundService.generatePaymentOrderByCompensation(context.getRefundOrderResponse().id(),  new MarkAsPayedRequest(new Payment(context.getPaymentResponse().id().toString(),
                context.getPaymentResponse().payeeType().name(),
                context.getPaymentResponse().amount(),
                context.getPaymentResponse().paymentMethod().name(),
                context.getPaymentResponse().paymentDate(),
                context.getPaymentResponse().transactionId(),
                context.getPaymentResponse().state().name(),
                context.getEmpleado().getBank(),
                context.getEmpleado().getAccountNumber())));
    }
}
