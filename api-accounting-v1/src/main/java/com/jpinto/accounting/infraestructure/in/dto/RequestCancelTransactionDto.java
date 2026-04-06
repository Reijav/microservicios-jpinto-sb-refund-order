package com.jpinto.accounting.infraestructure.in.dto;

import lombok.Data;

@Data
public class RequestCancelTransactionDto {
    private String idOrderRefund;
    private Long idTransaction;
}
