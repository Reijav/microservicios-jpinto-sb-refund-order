package com.jpinto.orchestator.services.sagapayment;


import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.PaymentService;
import com.jpinto.orchestator.services.TalentHumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(100)
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationDataStep implements SagaPaymentStep{
    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;
    private final PaymentService paymentService;

    @Override
    public void execute(PaymentSagaContext context) {
        context.setPaymentResponse(paymentService.getById(context.getPayPaymentRequest().getPaymentId().toString()));
        context.setRefundOrderResponse(orderRefundService.getByPaymentId(context.getPayPaymentRequest().getPaymentId()));
        context.setEmpleado(talentHumanService.findById(context.getRefundOrderResponse().employeeId()));
        context.setSupervisor(talentHumanService.findById(context.getRefundOrderResponse().approverId()));
    }

    @Override
    public void compensate(PaymentSagaContext context) {

    }
}
