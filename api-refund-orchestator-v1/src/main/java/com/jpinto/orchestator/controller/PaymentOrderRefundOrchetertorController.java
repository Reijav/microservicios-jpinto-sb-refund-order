package com.jpinto.orchestator.controller;

import com.jpinto.orchestator.controller.document.PagoOrdenReembolsoControllerDoc;
import com.jpinto.orchestator.dto.PayPaymentRequest;
import com.jpinto.orchestator.dto.PayPaymentResponse;
import com.jpinto.orchestator.services.PaymentOrderRefundWithCompensationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentOrderRefundOrchetertorController implements PagoOrdenReembolsoControllerDoc {
    private final PaymentOrderRefundWithCompensationService paymentOrderRefundWithCompensationService;

    @PreAuthorize("hasAuthority('CONTADOR')")
    @PatchMapping("/pay-order-refund")
    public PayPaymentResponse payOrderRefund(@RequestBody PayPaymentRequest request){
        return paymentOrderRefundWithCompensationService.payOrderRefund(request);
    }
}
