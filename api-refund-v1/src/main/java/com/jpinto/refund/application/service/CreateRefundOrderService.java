package com.jpinto.refund.application.service;

import com.jpinto.refund.application.dto.request.CreateRefundBillRequest;
import com.jpinto.refund.application.dto.request.CreateRefundOrderRequest;
import com.jpinto.refund.application.dto.response.RefundOrderResponse;
import com.jpinto.refund.application.mapper.RefundOrderMapper;
import com.jpinto.refund.application.port.in.CreateRefundOrderUseCase;
import com.jpinto.refund.domain.model.RefundBill;
import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.domain.repository.RefundOrderRepository;
import com.jpinto.refund.producer.order.created.OrderRefundCreatedProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateRefundOrderService implements CreateRefundOrderUseCase {

    private final RefundOrderRepository refundOrderRepository;
    private final OrderRefundCreatedProducer orderRefundCreatedProducer;

    public CreateRefundOrderService(RefundOrderRepository refundOrderRepository, OrderRefundCreatedProducer orderRefundCreatedProducer) {
        this.refundOrderRepository = refundOrderRepository;
        this.orderRefundCreatedProducer=orderRefundCreatedProducer;
    }

    @Override
    public RefundOrderResponse createRefundOrder(CreateRefundOrderRequest request) {
        List<RefundBill> bills = request.bills().stream()
                .map(this::toDomainBill)
                .toList();

        RefundOrder refundOrder = RefundOrder.create(request.employeeId(), request.employeeName(), request.supervisorId(), request.supervisorName(), request.motiveId(), bills);
        RefundOrder saved = refundOrderRepository.save(refundOrder);

        orderRefundCreatedProducer.produce(refundOrder);

        return RefundOrderMapper.toResponse(saved);
    }

    private RefundBill toDomainBill(CreateRefundBillRequest req) {
        return new RefundBill(
                null,
                null, // refundId will be assigned after aggregate save
                req.providerRuc(),
                req.providerName(),
                req.billNumber(),
                req.detail(),
                req.value(),
                req.billFile()
        );
    }
}
