package com.jpinto.payment.producer.process.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProcessedEvent {
    private String paymentId;
}
