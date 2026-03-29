package com.jpinto.orchestator.client.refund.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RefundBillResponse(
        UUID id,
        UUID refundId,
        String providerRuc,
        String providerName,
        String billNumber,
        String detail,
        BigDecimal value,
        String billFile
) {}
