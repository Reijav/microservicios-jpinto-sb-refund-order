package com.jpinto.orchestator.client.refund;

import com.jpinto.orchestator.client.refund.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefundRestClientService {

    private final RestClient refundRestClient;

    public RefundOrderResponse create(CreateRefundOrderRequest request) {

        return refundRestClient.post().
                uri("/refund-orders").
                body(request).
                retrieve().body(RefundOrderResponse.class);

    }

    public RefundOrderResponse getById(UUID id) {
        return refundRestClient.get().uri("/refund-orders/{id}", id.toString())
                .retrieve()
                .body(RefundOrderResponse.class);
    }


    public RefundOrderResponse getByPaymentId(@PathVariable String paymentId){
        return refundRestClient.get().uri("/refund-orders/get-by-paymentid/{paymentId}", paymentId)
                .retrieve()
                .body(RefundOrderResponse.class);
    }


    // ─── STATE TRANSITIONS ───────────────────────────────────────────────────

    public RefundOrderResponse approve( UUID id,  ApproveRefundRequest request) {
        return refundRestClient.put().
                uri("/refund-orders/{id}/approve", id).
                body(request).
                retrieve().body(RefundOrderResponse.class);

    }

   // @PatchMapping("/{id}/reject")
    public RefundOrderResponse reject( UUID id,ApproveRefundRequest request) {
        return refundRestClient.put().
                uri("/refund-orders/{id}/reject", id).
                body(request).
                retrieve().body(RefundOrderResponse.class);

    }


    public RefundOrderResponse generatePaymentOrder(UUID id, MarkAsPayedRequest request) {
        return refundRestClient.put().
                uri("/refund-orders/{id}/generate-payment-order", id).
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
