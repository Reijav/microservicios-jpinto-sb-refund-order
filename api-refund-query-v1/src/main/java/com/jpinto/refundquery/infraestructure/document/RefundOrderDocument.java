package com.jpinto.refundquery.infraestructure.document;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "refund-orders")
@Builder
public class RefundOrderDocument {
    @Id
    private final String id;
    private final LocalDate dateOrder;
    private final Long motiveId;
    private BigDecimal totalValue;
    private EmployeeDocument employee;
    private SupervisorDocument supervisor;
}
