package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.client.refund.dto.RejectRefundRequest;
import com.jpinto.orchestator.dto.RejectRefundOrchestatorRequest;
import com.jpinto.orchestator.exceptions.ActionNotAllowedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RejectOrderRefundOrchestatorService {

    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;
    private final NotificationService notificationService;

    public RefundOrderResponse rejectOrderRefund(RejectRefundOrchestatorRequest rejectRefundRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Long userId= (Long) ((JwtAuthenticationToken) Objects.requireNonNull(securityContext.getAuthentication())).getToken().getClaims().get("user-id");


        log.info("## Reject Order ## 1. Charge Order ");
        var orderRefund=  orderRefundService.getById(rejectRefundRequest.orderRefundId());

        log.info("## Reject Order ## 2. Charge Employee and Supervisor ");
        var employee= talentHumanService.findByUserId(orderRefund.employeeId());

        var supervisor= talentHumanService.findByUserId(orderRefund.approverId());

        if(!supervisor.getUserId().equals(userId)){
            throw new AuthorizationDeniedException("No está permitido para la revisión de la orden indicada.");
        }

        if(!supervisor.getId().equals(orderRefund.approverId())){
            throw new AuthorizationDeniedException("No se encuentra autorizado para revisar la orden de reembolso indicada.");
        }

        log.info("## Reject Order ## 3. Validations and Change of state ");

        var response=orderRefundService.reject(new RejectRefundRequest(supervisor.getId(), rejectRefundRequest.orderRefundId(), rejectRefundRequest.observation()));
        log.info("## Reject Order ## 4. Notification to employee");
        notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                .subjet("Nueva orden de reembolso por revisar")
                .ccEmail(List.of(employee.getEmail()))
                .body("La orden de reembolso: " + orderRefund.id() + " ha sido rechazada. Observación: ."+ rejectRefundRequest.observation())
                .build());
        return response;
    }
}
