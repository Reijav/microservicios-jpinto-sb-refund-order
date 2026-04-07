package com.jpinto.orchestator.services.sagaapprove;

import com.jpinto.orchestator.exceptions.ActionNotAllowedException;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.TalentHumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(100)
@RequiredArgsConstructor
public class ValidationOrderStep implements SagaApproveStep{
    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;

    @Override
    public void execute(ApproveOrderRefundSagaContext context) {
        log.info("## APPROVE ORDER ## 1. Charging data to Approve order refund");
        context.setRefundOrderResponse(orderRefundService.getById(context.getApproveRefundRequest().orderRefundId()));
        context.setEmpleado(talentHumanService.findById(context.getRefundOrderResponse().employeeId()));
        context.setSupervisor(talentHumanService.findById(context.getRefundOrderResponse().approverId()));
        if(!context.getApproveRefundRequest().approverId().equals(context.getSupervisor().getId())){
            throw new ActionNotAllowedException("La orden tiene como aprobador a un diferente usuario", null);
        }
        //TODO validate user autenticate with supervisor
    }

    @Override
    public void compensate(ApproveOrderRefundSagaContext context) {
        log.info("## APPROVE ORDER ## 1. Compensación validacion, chargind data.");
    }
}
