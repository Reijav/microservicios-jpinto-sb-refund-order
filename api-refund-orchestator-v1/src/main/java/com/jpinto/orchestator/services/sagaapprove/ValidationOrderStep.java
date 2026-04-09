package com.jpinto.orchestator.services.sagaapprove;

import com.jpinto.orchestator.exceptions.ActionNotAllowedException;
import com.jpinto.orchestator.services.OrderRefundService;
import com.jpinto.orchestator.services.TalentHumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Long userLogedId= (Long) ((JwtAuthenticationToken) Objects.requireNonNull(securityContext.getAuthentication())).getToken().getClaims().get("user-id");

        context.setRefundOrderResponse(orderRefundService.getById(context.getApproveRefundRequest().orderRefundId()));
        context.setEmpleado(talentHumanService.findById(context.getRefundOrderResponse().employeeId()));
        context.setSupervisor(talentHumanService.findById(context.getRefundOrderResponse().approverId()));

        if(!context.getSupervisor().getUserId().equals(userLogedId)){
            throw new AuthorizationDeniedException("El id del supervisor debe correspondes al id del usuario logeado");
        }
        if(!context.getRefundOrderResponse().approverId().equals(context.getSupervisor().getId())){
            throw new AuthorizationDeniedException("No se encuentra autorizado para revisar la orden de reembolso indicada.");
        }
    }

    @Override
    public void compensate(ApproveOrderRefundSagaContext context) {
        log.info("## APPROVE ORDER ## 1. Compensación validacion, chargind data.");
    }
}
