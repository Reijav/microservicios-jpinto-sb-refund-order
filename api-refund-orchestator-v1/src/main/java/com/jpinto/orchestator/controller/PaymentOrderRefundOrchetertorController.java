package com.jpinto.orchestator.controller;

import com.jpinto.orchestator.dto.PayPaymentRequest;
import com.jpinto.orchestator.dto.PayPaymentResponse;
import com.jpinto.orchestator.services.PaymentOrderRefundWithCompensationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentOrderRefundOrchetertorController {
    private final PaymentOrderRefundWithCompensationService paymentOrderRefundWithCompensationService;

    @PatchMapping("/pay-order-refund")
    public PayPaymentResponse payOrderRefund(@RequestBody PayPaymentRequest request){
        return paymentOrderRefundWithCompensationService.payOrderRefund(request);
    }
}
