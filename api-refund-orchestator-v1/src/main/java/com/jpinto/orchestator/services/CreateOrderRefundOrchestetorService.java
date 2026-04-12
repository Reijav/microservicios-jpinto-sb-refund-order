package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.refund.dto.CreateRefundOrderRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import com.jpinto.orchestator.exceptions.ServicioNotificacionesException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderRefundOrchestetorService {

    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;
    private final NotificationService notificationService;

    public RefundOrderResponse createOrderRefund(CreateOrderRefundRequest request){
        RefundOrderResponse response=null;
        try {

            SecurityContext securityContext = SecurityContextHolder.getContext();
            Long userId = (Long) ((JwtAuthenticationToken) Objects.requireNonNull(securityContext.getAuthentication())).getToken().getClaims().get("user-id");

            log.info("## CREATE ORDER ## 1. Consulta empleado");
            var employee = talentHumanService.findByUserId(userId);

            log.info("## CREATE ORDER ## 2. Consulta supervisor");
            var supervisor = talentHumanService.findById(employee.getInmediateSupervisorId());
            log.info("## CREATE ORDER ## 3. Creacion orden de reembolso");
            response = orderRefundService.create(new CreateRefundOrderRequest(employee.getId(), employee.getFullName(), supervisor.getId(), supervisor.getFullName(), request.getMotiveId(), request.getBills()));
            log.info("## CREATE ORDER ## 4. Encolar envio de mail");
            notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                    .subjet("Nueva orden de reembolso por revisar")
                    .toEmail(List.of(supervisor.getEmail()))
                    .body("Se ha generado una nueva orden de reembolso por parte de " + supervisor.getFullName() + ". Realizar la revisión respectiva.")
                    .build());

        }catch (ServicioNotificacionesException ex){
            log.error("Error notificaciones: " + ex.getMessage(), ex);
        }
        return response;
    }

}
