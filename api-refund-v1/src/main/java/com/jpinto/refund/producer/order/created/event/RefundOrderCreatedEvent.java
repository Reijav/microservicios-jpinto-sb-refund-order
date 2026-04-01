package com.jpinto.refund.producer.order.created.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class RefundOrderCreatedEvent {
    private final UUID id;
    private final LocalDate dateOrder;
    private final Long motiveId;
    private BigDecimal totalValue;
    private Employee employee;
    private Supervisor supervisor;
}
