package com.jpinto.orchestator.client.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateTransactionDto {
    private String descripcion;
    private List<TransactionLineDto> lineDtoList;
}
