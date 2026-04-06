package com.jpinto.refund.producer.order.approved.event;

import com.jpinto.refund.application.dto.request.Payment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderApprovedEvent {
    private Payment payment;
    private String idOrderRefund;
}
