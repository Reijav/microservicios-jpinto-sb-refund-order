package com.jpinto.refund.application.port.in;

import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface GetRefundOrderUseCase {

    RefundOrderResponse getRefundOrderById(UUID id);

    List<RefundOrderResponse> getAllRefundOrders();

    RefundOrderResponse getByPaymentId(String paymentId);
}
