package com.jpinto.refundquery.controllers.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RefundOrderDto {
    @Id
    private final String id;
    private final LocalDate dateOrder;
    private final Long motiveId;
    private BigDecimal totalValue;
    private EmployeeDto employee;
    private SupervisorDto supervisor;
    private PaymentDto payment;
    private String state;
}
