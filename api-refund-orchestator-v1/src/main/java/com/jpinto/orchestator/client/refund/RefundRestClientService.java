package com.jpinto.orchestator.client.refund;

import com.jpinto.orchestator.client.refund.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefundRestClientService {

    private final RestClient refundRestClient;

    public RefundOrderResponse create(CreateRefundOrderRequest request) {
        log.info("Calling create order refund.");
        return refundRestClient.post().
                uri("/refund-orders").
                body(request).
                retrieve().body(RefundOrderResponse.class);

    }

    public RefundOrderResponse getById(UUID id) {
        log.info("Calling get order refund by id.");
        return refundRestClient.get().uri("/refund-orders/{id}", id.toString())
                .retrieve()
                .body(RefundOrderResponse.class);
    }


    public RefundOrderResponse getByPaymentId(@PathVariable String paymentId){
        log.info("Calling get order refund by payment id.");
        return refundRestClient.get().uri("/refund-orders/get-by-paymentid/{paymentId}", paymentId)
                .retrieve()
                .body(RefundOrderResponse.class);
    }

    public RefundOrderResponse approve( UUID id) {
        log.info("Calling approve order.");
        return refundRestClient.put().
                uri("/refund-orders/{id}/approve", id).
                retrieve().body(RefundOrderResponse.class);

    }


    public RefundOrderResponse reject( UUID id,RejectRefundRequest request) {
        log.info("Calling reject order refund.");
        return refundRestClient.put().
                uri("/refund-orders/{id}/reject", id).
                body(request).
                retrieve().body(RefundOrderResponse.class);

    }


    public RefundOrderResponse generatePaymentOrder(UUID id, MarkAsPayedRequest request) {
        log.info("Calling generate payment order.");
        return refundRestClient.put().
                uri("/refund-orders/{id}/generate-payment-order", id.toString()).
                body(request).
                retrieve().body(RefundOrderResponse.class);
    }


    public RefundOrderResponse markAsPayed(UUID id) {
        return refundRestClient.put().
                uri("/refund-orders/{id}/mark-as-payed", id).
                retrieve().
                body(RefundOrderResponse.class);
    }


    public RefundOrderResponse rollbackState(@PathVariable UUID id,
                                                             @RequestBody RollbackStateRequest request) {
        return refundRestClient.put()
                .uri("/refund-orders/{id}/rollback-state", id)
                .body(request)
                .retrieve()
                .body(RefundOrderResponse.class);

    }
}
