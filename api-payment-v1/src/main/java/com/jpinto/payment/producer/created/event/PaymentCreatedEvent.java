package com.jpinto.payment.producer.created.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCreatedEvent {
    private String orderRefundId;
    private String paymentId;
}
