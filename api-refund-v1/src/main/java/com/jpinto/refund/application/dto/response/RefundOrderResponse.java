package com.jpinto.refund.application.dto.response;

import com.jpinto.refund.domain.model.RefundState;

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
        String observation,
        List<RefundBillResponse> bills
) {}
