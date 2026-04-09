package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.refund.dto.ApproveRefundRequest;
import com.jpinto.orchestator.client.refund.dto.RejectRefundRequest;
import com.jpinto.orchestator.dto.ApprovedRefundResponse;
import com.jpinto.orchestator.exceptions.StopSagaException;
import com.jpinto.orchestator.services.sagaapprove.ApproveOrderRefundSagaContext;
import com.jpinto.orchestator.services.sagaapprove.SagaApproveStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApproveOrderRefundWithCompensationService {
    private final List<SagaApproveStep > steps;


    public ApprovedRefundResponse aproveOrderRefund(ApproveRefundRequest request){

        log.info("Starting approving process creation with compensations");

        var context = new ApproveOrderRefundSagaContext(request);

        List<SagaApproveStep> executedSteps = new ArrayList<>();

        try {
            for (var step : steps) {
                step.execute(context);
                executedSteps.add(step);
            }

            log.info("Order approved completed successfully with compensations");
            return ApprovedRefundResponse.builder().idOrderRefund(request.orderRefundId())
                    .idPayment(context.getPaymentResponse().id())
                    .build();
        }catch (AuthorizationDeniedException ex) {
            throw  ex;
        } catch (Exception ex) {

            Collections.reverse(executedSteps);

            for (SagaApproveStep step : executedSteps) {
                try {
                    step.compensate(context);
                } catch (Exception compEx) {
                    log.error("Compensation failed in step {}: {}", step.getClass().getSimpleName(), compEx.getMessage());
                    //Guardar en una tabla de compensaciones fallidas para reintentos manuales
                }
            }

            throw new StopSagaException("Order approved failed, triggering compensations: " + ex.getMessage(), ex);
        }


    }
}
