package com.jpinto.refundquery.listener.order.rejected.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundOrderRejectedEvent {
    private String id;
    private String observation;
}
