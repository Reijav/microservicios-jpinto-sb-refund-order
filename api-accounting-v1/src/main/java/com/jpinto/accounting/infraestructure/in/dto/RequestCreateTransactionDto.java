package com.jpinto.accounting.infraestructure.in.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestCreateTransactionDto {
    private String descripcion;
    private List<TransactionLineDto> lineDtoList;
}
