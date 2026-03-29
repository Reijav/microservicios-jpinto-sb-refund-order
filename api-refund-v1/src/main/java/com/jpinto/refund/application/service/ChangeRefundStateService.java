package com.jpinto.refund.application.service;

import com.jpinto.refund.application.dto.request.ApproveRefundRequest;
import com.jpinto.refund.application.dto.request.MarkAsPayedRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.mapper.RefundOrderMapper;
import com.jpinto.refund.application.port.in.ChangeRefundStateUseCase;
import com.jpinto.refund.domain.exception.RefundOrderNotFoundException;
import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.domain.model.RefundState;
import com.jpinto.refund.domain.repository.RefundOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ChangeRefundStateService implements ChangeRefundStateUseCase {

    private final RefundOrderRepository refundOrderRepository;

    public ChangeRefundStateService(RefundOrderRepository refundOrderRepository) {
        this.refundOrderRepository = refundOrderRepository;
    }

    @Override
    public RefundOrderResponse approveRefund(UUID refundId, ApproveRefundRequest request) {
        RefundOrder order = findOrThrow(refundId);
        order.approve(request.approverId());
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse roolbackState(UUID refundId, RefundState rollbackState) {
        RefundOrder order = findOrThrow(refundId);
        order.roolbackState(rollbackState);
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse rejectRefund(UUID refundId, ApproveRefundRequest request) {
        RefundOrder order = findOrThrow(refundId);
        order.reject(request.approverId());
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse generatePaymentOrder(UUID refundId,  MarkAsPayedRequest request) {
        RefundOrder order = findOrThrow(refundId);
        order.generatePaymentOrder(request.paymentId());
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse markAsPayed(UUID refundId) {
        RefundOrder order = findOrThrow(refundId);
        order.markAsPayed();
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    private RefundOrder findOrThrow(UUID refundId) {
        return refundOrderRepository.findById(refundId)
                .orElseThrow(() -> new RefundOrderNotFoundException(refundId));
    }
}
