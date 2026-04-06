package com.jpinto.refund.application.port.in;

import com.jpinto.refund.application.dto.request.ApproveRefundRequest;
import com.jpinto.refund.application.dto.request.MarkPayRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.domain.model.RefundState;

import java.util.UUID;

public interface ChangeRefundStateUseCase {

    RefundOrderResponse approveRefund(UUID refundId, ApproveRefundRequest request);

    RefundOrderResponse rejectRefund(UUID refundId, ApproveRefundRequest request);

    RefundOrderResponse registerPaymentOrder(UUID refundId, MarkPayRequest request);

    RefundOrderResponse markAsPayed(UUID refundId);

    RefundOrderResponse roolbackState(UUID refundId, RefundState rollbackState);

}
