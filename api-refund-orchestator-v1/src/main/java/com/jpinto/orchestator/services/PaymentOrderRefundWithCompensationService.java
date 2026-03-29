package com.jpinto.orchestator.services;

import com.jpinto.orchestator.dto.PayPaymentRequest;
import com.jpinto.orchestator.dto.PayPaymentResponse;
import com.jpinto.orchestator.exceptions.StopSagaException;
import com.jpinto.orchestator.services.sagapayment.PaymentSagaContext;
import com.jpinto.orchestator.services.sagapayment.SagaPaymentStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentOrderRefundWithCompensationService {
    private final OrderRefundService orderRefundService;
    private final List<SagaPaymentStep> steps;

    public PayPaymentResponse payOrderRefund(PayPaymentRequest payPaymentRequest){

        log.info("Starting order creation with compensations");

        var context = new PaymentSagaContext(payPaymentRequest);

        List<SagaPaymentStep> executedSteps = new ArrayList<>();

        try {
            for (var step : steps) {
                step.execute(context);
                executedSteps.add(step);
            }

            log.info("Payment order completed successfully with compensations");
            return new PayPaymentResponse( context.getTransactionDto().getIdTransaction(), context.getPayPaymentRequest().getPaymentId(), context.getRefundOrderResponse().id().toString());
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            Collections.reverse(executedSteps);

            for (SagaPaymentStep step : executedSteps) {
                try {
                    step.compensate(context);
                } catch (Exception compEx) {
                    log.error("Compensation failed in step {}: {}", step.getClass().getSimpleName(), compEx.getMessage());
                    //Guardar en una tabla de compensaciones fallidas para reintentos manuales
                }
            }

            throw new StopSagaException("Payment order failed, triggering compensations: " + ex.getMessage(), ex);
        }
    }

}
