package com.jpinto.refund.application.service;

import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.mapper.RefundOrderMapper;
import com.jpinto.refund.application.port.in.GetRefundOrderUseCase;
import com.jpinto.refund.domain.exception.RefundOrderNotFoundException;
import com.jpinto.refund.domain.repository.RefundOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetRefundOrderService implements GetRefundOrderUseCase {

    private final RefundOrderRepository refundOrderRepository;

    public GetRefundOrderService(RefundOrderRepository refundOrderRepository) {
        this.refundOrderRepository = refundOrderRepository;
    }

    @Override
    public RefundOrderResponse getRefundOrderById(UUID id) {
        return refundOrderRepository.findById(id)
                .map(RefundOrderMapper::toResponse)
                .orElseThrow(() -> new RefundOrderNotFoundException(id));
    }

    @Override
    public List<RefundOrderResponse> getAllRefundOrders() {
        return refundOrderRepository.findAll().stream()
                .map(RefundOrderMapper::toResponse)
                .toList();
    }

    @Override
    public RefundOrderResponse getByPaymentId(String paymentId) {
        return refundOrderRepository.findByPaymentId(paymentId).map(RefundOrderMapper::toResponse)
                .orElseThrow(() -> new RefundOrderNotFoundException(paymentId));
    }
}
