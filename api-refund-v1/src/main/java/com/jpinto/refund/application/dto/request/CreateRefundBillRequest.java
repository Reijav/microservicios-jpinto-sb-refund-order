package com.jpinto.refund.application.dto.request;

import java.math.BigDecimal;

public record CreateRefundBillRequest(
        String providerRuc,
        String providerName,
        String billNumber,
        String detail,
        BigDecimal value,
        String billFile
) {}
