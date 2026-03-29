package com.jpinto.refund.application.mapper;

import com.jpinto.refund.application.dto.response.RefundBillResponse;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.domain.model.RefundBill;
import com.jpinto.refund.domain.model.RefundOrder;

import java.util.List;

public class RefundOrderMapper {

    private RefundOrderMapper() {}

    public static RefundOrderResponse toResponse(RefundOrder order) {
        List<RefundBillResponse> billResponses = order.getBills().stream()
                .map(RefundOrderMapper::toBillResponse)
                .toList();

        return new RefundOrderResponse(
                order.getId(),
                order.getEmployeeId(),
                order.getDateOrder(),
                order.getMotiveId(),
                order.getTotalValue(),
                order.getApproverId(),
                order.getState(),
                order.getPaymentId(),
                billResponses
        );
    }

    public static RefundBillResponse toBillResponse(RefundBill bill) {
        return new RefundBillResponse(
                bill.getId(),
                bill.getRefundId(),
                bill.getProviderRuc(),
                bill.getProviderName(),
                bill.getBillNumber(),
                bill.getDetail(),
                bill.getValue(),
                bill.getBillFile()
        );
    }
}
