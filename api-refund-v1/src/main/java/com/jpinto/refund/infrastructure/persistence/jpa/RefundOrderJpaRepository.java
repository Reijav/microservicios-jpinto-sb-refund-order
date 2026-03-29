package com.jpinto.refund.infrastructure.persistence.jpa;

import com.jpinto.refund.infrastructure.persistence.entity.RefundOrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefundOrderJpaRepository extends JpaRepository<RefundOrderJpaEntity, UUID> {

    Optional<RefundOrderJpaEntity> findByPaymentId(String paymentId);
}
