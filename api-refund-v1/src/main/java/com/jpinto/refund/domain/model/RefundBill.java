package com.jpinto.refund.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class RefundBill {

    private final UUID id;
    private final UUID refundId;
    private final String providerRuc;
    private final String providerName;
    private final String billNumber;
    private final String detail;
    private final BigDecimal value;
    private final String billFile;

    public RefundBill(UUID id, UUID refundId, String providerRuc, String providerName,
                      String billNumber, String detail, BigDecimal value, String billFile) {
        if (providerRuc == null || providerRuc.isBlank()) throw new IllegalArgumentException("providerRuc must not be blank");
        if (providerName == null || providerName.isBlank()) throw new IllegalArgumentException("providerName must not be blank");
        if (billNumber == null || billNumber.isBlank()) throw new IllegalArgumentException("billNumber must not be blank");
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("value must be positive");
        this.id = id != null ? id : UUID.randomUUID();
        this.refundId = refundId;
        this.providerRuc = providerRuc;
        this.providerName = providerName;
        this.billNumber = billNumber;
        this.detail = detail;
        this.value = value;
        this.billFile = billFile;
    }

    public UUID getId() { return id; }
    public UUID getRefundId() { return refundId; }
    public String getProviderRuc() { return providerRuc; }
    public String getProviderName() { return providerName; }
    public String getBillNumber() { return billNumber; }
    public String getDetail() { return detail; }
    public BigDecimal getValue() { return value; }
    public String getBillFile() { return billFile; }
}
