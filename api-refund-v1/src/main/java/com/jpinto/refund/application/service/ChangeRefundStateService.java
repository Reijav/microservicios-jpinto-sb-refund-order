package com.jpinto.refund.application.service;

import com.jpinto.refund.application.dto.request.ApproveRefundRequest;
import com.jpinto.refund.application.dto.request.MarkPayRequest;
import com.jpinto.refund.application.dto.request.Payment;
import com.jpinto.refund.application.dto.request.RejectRefundRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.mapper.RefundOrderMapper;
import com.jpinto.refund.application.port.in.ChangeRefundStateUseCase;
import com.jpinto.refund.domain.exception.RefundOrderNotFoundException;
import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.domain.model.RefundState;
import com.jpinto.refund.domain.repository.RefundOrderRepository;
import com.jpinto.refund.producer.order.approved.OrderRefundApprovedProducer;
import com.jpinto.refund.producer.order.rejected.OrderRefundRejectedProducter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeRefundStateService implements ChangeRefundStateUseCase {

    private final RefundOrderRepository refundOrderRepository;
    private final OrderRefundApprovedProducer orderRefundApprovedProducer;
    private final OrderRefundRejectedProducter orderRefundRejectedProducter;

    @Override
    public RefundOrderResponse approveRefund(UUID refundId) {
        RefundOrder order = findOrThrow(refundId);
        order.approve();
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse roolbackState(UUID refundId, RefundState rollbackState) {
        RefundOrder order = findOrThrow(refundId);
        order.roolbackState(rollbackState);
        return RefundOrderMapper.toResponse(refundOrderRepository.save(order));
    }

    @Override
    public RefundOrderResponse rejectRefund(UUID refundId, RejectRefundRequest request) {
        RefundOrder order = findOrThrow(refundId);
        order.reject(request.observation());
        var refundOrderJpa= refundOrderRepository.save(order);
        orderRefundRejectedProducter.produce(order.getId().toString(), request.observation());
        return RefundOrderMapper.toResponse(refundOrderJpa);
    }

    @Override
    public RefundOrderResponse registerPaymentOrder(UUID refundId, MarkPayRequest request) {
        RefundOrder order = findOrThrow(refundId);
        order.generatePaymentOrder(request.payment().id());
        var response= RefundOrderMapper.toResponse(refundOrderRepository.save(order));
        orderRefundApprovedProducer.produce(order, new Payment(request.payment().id(), request.payment().payeeType(),
                request.payment().amount(), request.payment().paymentMethod(), request.payment().paymentDate(), request.payment().transactionId(),
                request.payment().state(), request.payment().bank(), request.payment().savingAccount()));
        return response;
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
