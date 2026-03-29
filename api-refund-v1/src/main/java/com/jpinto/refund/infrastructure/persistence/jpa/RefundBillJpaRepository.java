package com.jpinto.refund.infrastructure.persistence.jpa;

import com.jpinto.refund.infrastructure.persistence.entity.RefundBillJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefundBillJpaRepository extends JpaRepository<RefundBillJpaEntity, UUID> {

    List<RefundBillJpaEntity> findByRefundOrderId(UUID refundId);

    void deleteByRefundOrderId(UUID refundId);
}
