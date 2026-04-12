package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.notification.dto.RequestSendMail;
import com.jpinto.orchestator.client.payment.PaymentRestClientService;
import com.jpinto.orchestator.client.payment.dto.CreatePaymentRequest;
import com.jpinto.orchestator.client.payment.dto.PaymentResponse;
import com.jpinto.orchestator.client.payment.dto.ProcessPaymentDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRestClientService paymentRestClientService;
    private final NotificationService notificationService;

    public PaymentResponse create(CreatePaymentRequest request) {
        return paymentRestClientService.create(request);
    }

    public PaymentResponse getById(String id) {
        return paymentRestClientService.getById(id);
    }

    public PaymentResponse cancel(UUID id) {
        return paymentRestClientService.cancel(id.toString());
    }

    @CircuitBreaker(name = "cbPayOrderRefund", fallbackMethod = "fallbackMethodProcess")
    public PaymentResponse process(UUID id, ProcessPaymentDto processPaymentDto) {
        return paymentRestClientService.process(id.toString(), processPaymentDto);
    }

    public PaymentResponse fallbackMethodProcess(UUID id, ProcessPaymentDto processPaymentDto, CallNotPermittedException ex)  {
        log.error("Servicio 'PaymentService' no disponible: " + ex.getMessage());
        notificationService.encolarEnvioHtmlMail(RequestSendMail.builder()
                .subjet("Servicio 'PaymentService' no disponible")
                .body("Se ha identificado error en el servicio de Pagos 'PaymentService' " +
                        ": " + ex.getMessage() )
                .toEmail(List.of("soporte@jpinto.com"))
                .build());

        throw ex;
    }
}
