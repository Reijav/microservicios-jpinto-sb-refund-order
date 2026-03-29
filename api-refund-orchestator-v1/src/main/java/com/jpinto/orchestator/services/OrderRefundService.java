package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.refund.RefundRestClientService;
import com.jpinto.orchestator.client.refund.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderRefundService {
    private final RefundRestClientService refundRestClientService;

    public RefundOrderResponse getByPaymentId(String paymentId) {
        return refundRestClientService.getByPaymentId(paymentId);
    }

    public RefundOrderResponse getById(UUID id) {
        return refundRestClientService.getById(id);
    }

    public RefundOrderResponse rollbackState(UUID id, RollbackStateRequest request) {
        return refundRestClientService.rollbackState(id, request);
    }

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
}
