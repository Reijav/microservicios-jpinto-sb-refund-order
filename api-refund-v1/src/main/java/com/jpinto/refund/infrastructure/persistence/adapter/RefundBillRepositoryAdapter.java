package com.jpinto.refund.infrastructure.persistence.adapter;

import com.jpinto.refund.domain.model.RefundBill;
import com.jpinto.refund.domain.repository.RefundBillRepository;
import com.jpinto.refund.infrastructure.persistence.jpa.RefundBillJpaRepository;
import com.jpinto.refund.infrastructure.persistence.mapper.RefundOrderJpaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RefundBillRepositoryAdapter implements RefundBillRepository {

    private final RefundBillJpaRepository jpaRepository;

    public RefundBillRepositoryAdapter(RefundBillJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RefundBill save(RefundBill refundBill) {
        // Bills are managed via cascade from RefundOrder aggregate; direct save not needed
        throw new UnsupportedOperationException("Bills are managed through the RefundOrder aggregate");
    }

    @Override
    public List<RefundBill> findByRefundId(UUID refundId) {
        return jpaRepository.findByRefundOrderId(refundId).stream()
                .map(RefundOrderJpaMapper::toBillDomain)
                .toList();
    }

    @Override
    public void deleteByRefundId(UUID refundId) {
        jpaRepository.deleteByRefundOrderId(refundId);
    }
}
