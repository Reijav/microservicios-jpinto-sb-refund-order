package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.refund.dto.CreateRefundOrderRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderRefundOrchestetorService {

    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;
    private final NotificationService notificationService;

    public RefundOrderResponse createOrderRefund(CreateOrderRefundRequest request){
        log.info("Consulta empleado");
        var employee=talentHumanService.findById(request.getEmployeeId());
        log.info("Consulta supervisor");
        var supervisor= talentHumanService.findById(employee.getInmediateSupervisorId());
        log.info("Creacion orden de reembolso");
        var response= orderRefundService.create(new CreateRefundOrderRequest(request.getEmployeeId(), employee.getFullName(), supervisor.getId(), supervisor.getFullName(), request.getMotiveId(), request.getBills()));
        log.info("Encolar envio de mail");
        notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                        .subjet("Nueva orden de reembolso por revisar")
                        .ccEmail(List.of(supervisor.getEmail()))
                        .body("Se ha generado una nueva orden de reembolso por parte de " + supervisor.getFullName() + ". Realizar la revisión respectiva.")
                .build());
        return response;
    }

}
