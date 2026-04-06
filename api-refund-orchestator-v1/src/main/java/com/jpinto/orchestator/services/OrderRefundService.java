package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.refund.RefundRestClientService;
import com.jpinto.orchestator.client.refund.dto.*;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderRefundService {
    private final RefundRestClientService refundRestClientService;
    private final NotificationService notificationService;

    public RefundOrderResponse getByPaymentId(String paymentId) {
        return refundRestClientService.getByPaymentId(paymentId);
    }

    public RefundOrderResponse getById(UUID id) {
        return refundRestClientService.getById(id);
    }

    public RefundOrderResponse rollbackState(UUID id, RollbackStateRequest request) {
        return refundRestClientService.rollbackState(id, request);
    }


    @CircuitBreaker(name = "cbCreateOrderRefund", fallbackMethod = "createOrderFallBack")
    public RefundOrderResponse create(CreateRefundOrderRequest request) {
        return refundRestClientService.create(request);
    }

    public RefundOrderResponse approve(UUID id, ApproveRefundRequest request) {
        return refundRestClientService.approve(id, request);
    }

    public RefundOrderResponse reject(UUID id, ApproveRefundRequest request) {
        return refundRestClientService.reject(id, request);
    }

    public RefundOrderResponse generatePaymentOrder(UUID id, MarkAsPayedRequest request) {
        return refundRestClientService.generatePaymentOrder(id, request);
    }

    public RefundOrderResponse markAsPayed(UUID id) {
        return refundRestClientService.markAsPayed(id);
    }


    public RefundOrderResponse createOrderFallBack(CreateRefundOrderRequest request, CallNotPermittedException ex){
        log.error("Servicio 'OrderRefundService' no disponible: " + ex.getMessage());
        notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                        .subjet("Servicio 'OrderRefundService' no disponible")
                        .body("Se ha identificado error en el servicio de Reembolsos 'OrderRefundService' " +
                                ": " + ex.getMessage() )
                        .toEmail(List.of("soporte@jpinto.com"))

                .build());

        throw ex;

    }
}
