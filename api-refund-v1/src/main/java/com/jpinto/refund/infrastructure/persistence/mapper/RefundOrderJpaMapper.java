package com.jpinto.refund.infrastructure.persistence.mapper;

import com.jpinto.refund.domain.model.RefundBill;
import com.jpinto.refund.domain.model.RefundOrder;
import com.jpinto.refund.infrastructure.persistence.entity.RefundBillJpaEntity;
import com.jpinto.refund.infrastructure.persistence.entity.RefundOrderJpaEntity;

import java.util.List;
import java.util.UUID;

public class RefundOrderJpaMapper {

    private RefundOrderJpaMapper() {}

    public static RefundOrderJpaEntity toJpaEntity(RefundOrder domain) {
        RefundOrderJpaEntity entity = new RefundOrderJpaEntity();
        entity.setId(domain.getId());
        entity.setEmployeeId(domain.getEmployeeId());
        entity.setDateOrder(domain.getDateOrder());
        entity.setMotiveId(domain.getMotiveId());
        entity.setTotalValue(domain.getTotalValue());
        entity.setApproverId(domain.getApproverId());
        entity.setState(domain.getState());
        entity.setPaymentId(domain.getPaymentId());

        List<RefundBillJpaEntity> billEntities = domain.getBills().stream()
                .map(bill -> toBillJpaEntity(bill, entity))
                .toList();
        entity.getBills().clear();
        entity.getBills().addAll(billEntities);

        return entity;
    }

    public static RefundBillJpaEntity toBillJpaEntity(RefundBill bill, RefundOrderJpaEntity refundOrderEntity) {
        RefundBillJpaEntity entity = new RefundBillJpaEntity();
        entity.setId(bill.getId() != null ? bill.getId() : UUID.randomUUID());
        entity.setRefundOrder(refundOrderEntity);
        entity.setProviderRuc(bill.getProviderRuc());
        entity.setProviderName(bill.getProviderName());
        entity.setBillNumber(bill.getBillNumber());
        entity.setDetail(bill.getDetail());
        entity.setValue(bill.getValue());
        entity.setBillFile(bill.getBillFile());
        return entity;
    }

    public static RefundOrder toDomain(RefundOrderJpaEntity entity) {
        List<RefundBill> bills = entity.getBills().stream()
                .map(RefundOrderJpaMapper::toBillDomain)
                .toList();

        return new RefundOrder(
                entity.getId(),
                entity.getEmployeeId(),
                null,
                entity.getDateOrder(),
                entity.getMotiveId(),
                entity.getTotalValue(),
                entity.getApproverId(),
                null,
                entity.getState(),
                entity.getPaymentId(),
                bills
        );
    }

    public static RefundBill toBillDomain(RefundBillJpaEntity entity) {
        return new RefundBill(
                entity.getId(),
                entity.getRefundOrder().getId(),
                entity.getProviderRuc(),
                entity.getProviderName(),
                entity.getBillNumber(),
                entity.getDetail(),
                entity.getValue(),
                entity.getBillFile()
        );
    }
}
