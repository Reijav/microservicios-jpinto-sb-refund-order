package com.jpinto.payment.infrastructure.persistence.adapter;

import com.jpinto.payment.domain.model.Payment;
import com.jpinto.payment.domain.repository.PaymentRepository;
import com.jpinto.payment.infrastructure.persistence.jpa.PaymentJpaRepository;
import com.jpinto.payment.infrastructure.persistence.mapper.PaymentJpaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    public PaymentRepositoryAdapter(PaymentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        var entity = PaymentJpaMapper.toJpaEntity(payment);
        var saved  = jpaRepository.save(entity);
        return PaymentJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(PaymentJpaMapper::toDomain);
    }

    @Override
    public List<Payment> findAll() {
        return jpaRepository.findAll().stream()
                .map(PaymentJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
