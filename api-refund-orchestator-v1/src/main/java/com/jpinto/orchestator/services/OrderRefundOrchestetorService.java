package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.refund.dto.CreateRefundOrderRequest;
import com.jpinto.orchestator.client.refund.dto.RefundOrderResponse;
import com.jpinto.orchestator.dto.CreateOrderRefundRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderRefundOrchestetorService {

    private final OrderRefundService orderRefundService;
    private final TalentHumanService talentHumanService;
    private final NotificationService notificationService;

    public RefundOrderResponse createOrderRefund(CreateOrderRefundRequest request){
        var employee=talentHumanService.findById(request.getEmployeeId());
        var supervisor= talentHumanService.findById(employee.getInmediateSupervisorId());
        var response= orderRefundService.create(new CreateRefundOrderRequest(request.getEmployeeId(), employee.getFullName(), supervisor.getId(), supervisor.getFullName(), request.getMotiveId(), request.getBills()));
        notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                        .subjet("Nueva orden de reembolso por revisar")
                        .ccEmail(List.of(supervisor.getEmail()))
                        .body("Se ha generado una nueva orden de reembolso por parte de " + supervisor.getFullName() + ". Realizar la revisión respectiva.")
                .build());

        return response;
    }

}
