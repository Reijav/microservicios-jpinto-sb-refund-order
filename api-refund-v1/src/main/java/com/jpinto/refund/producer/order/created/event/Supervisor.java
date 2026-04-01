package com.jpinto.refund.producer.order.created.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Supervisor {
    private Long id;
    private String name;
}
