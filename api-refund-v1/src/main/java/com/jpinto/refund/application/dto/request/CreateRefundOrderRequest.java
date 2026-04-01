package com.jpinto.refund.application.dto.request;

import java.util.List;

public record CreateRefundOrderRequest(
        Long employeeId,
        String employeeName,
        Long supervisorId,
        String supervisorName,
        Long motiveId,
        List<CreateRefundBillRequest> bills
) {}
