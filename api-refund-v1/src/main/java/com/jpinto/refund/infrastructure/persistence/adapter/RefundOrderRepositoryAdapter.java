package com.jpinto.refund.infrastructure.persistence.adapter;

import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.domain.repository.RefundOrderRepository;
import com.jpinto.refund.infrastructure.persistence.jpa.RefundOrderJpaRepository;
import com.jpinto.refund.infrastructure.persistence.mapper.RefundOrderJpaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefundOrderRepositoryAdapter implements RefundOrderRepository {

    private final RefundOrderJpaRepository jpaRepository;

    public RefundOrderRepositoryAdapter(RefundOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RefundOrder save(RefundOrder refundOrder) {
        var jpaEntity = RefundOrderJpaMapper.toJpaEntity(refundOrder);
        var saved = jpaRepository.save(jpaEntity);
        return RefundOrderJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<RefundOrder> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(RefundOrderJpaMapper::toDomain);
    }

    @Override
    public List<RefundOrder> findAll() {
        return jpaRepository.findAll().stream()
                .map(RefundOrderJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<RefundOrder> findByPaymentId(String paymentId) {
        return jpaRepository.findByPaymentId(paymentId).map(RefundOrderJpaMapper::toDomain);
    }
}
