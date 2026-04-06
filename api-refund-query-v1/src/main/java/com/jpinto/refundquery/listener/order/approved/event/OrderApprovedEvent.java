package com.jpinto.refundquery.listener.order.approved.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderApprovedEvent {
    private Payment payment;
    private String idOrderRefund;
}
