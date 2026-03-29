package com.jpinto.refund.domain.repository;

import com.jpinto.refund.domain.model.RefundBill;

import java.util.List;
import java.util.UUID;

public interface RefundBillRepository {

    RefundBill save(RefundBill refundBill);

    List<RefundBill> findByRefundId(UUID refundId);

    void deleteByRefundId(UUID refundId);
}
