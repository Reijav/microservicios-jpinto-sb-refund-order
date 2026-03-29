package com.jpinto.orchestator.client.refund.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RefundOrderResponse(
        UUID id,
        Long employeeId,
        LocalDate dateOrder,
        Long motiveId,
        BigDecimal totalValue,
        Long approverId,
        RefundState state,
        String paymentId,
        List<RefundBillResponse> bills
) {}
