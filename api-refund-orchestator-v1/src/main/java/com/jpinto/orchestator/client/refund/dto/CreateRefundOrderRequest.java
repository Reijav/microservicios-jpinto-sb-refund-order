package com.jpinto.orchestator.client.refund.dto;


import java.util.List;

public record CreateRefundOrderRequest(
        Long employeeId,
        String employeeName,
        Long supervisorId,
        String supervisorName,
        Long motiveId,
        List<CreateRefundBillRequest> bills
) {}
