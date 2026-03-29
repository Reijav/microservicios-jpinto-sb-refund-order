package com.jpinto.orchestator.client.refund.dto;

import java.math.BigDecimal;

public record CreateRefundBillRequest(
        String providerRuc,
        String providerName,
        String billNumber,
        String detail,
        BigDecimal value,
        String billFile
) {}
