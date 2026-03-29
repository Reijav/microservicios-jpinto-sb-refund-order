package com.jpinto.refund.domain.repository;

import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.domain.model.RefundOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefundOrderRepository {

    RefundOrder save(RefundOrder refundOrder);

    Optional<RefundOrder> findById(UUID id);

    List<RefundOrder> findAll();

    void deleteById(UUID id);

    Optional<RefundOrder> findByPaymentId(String paymentId);
}
