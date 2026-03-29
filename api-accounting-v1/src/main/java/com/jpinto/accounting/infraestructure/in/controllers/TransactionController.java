package com.jpinto.accounting.infraestructure.in.controllers;

import com.jpinto.accounting.application.port.in.TransactionUseCase;
import com.jpinto.accounting.infraestructure.in.dto.RequestCreateTransactionDto;
import com.jpinto.accounting.infraestructure.in.dto.ResponseCancelTransactionDto;
import com.jpinto.accounting.infraestructure.in.dto.ResponseCreateTransactionDto;
import com.jpinto.accounting.infraestructure.mappers.LineDtoToDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacciones")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionUseCase transactionUseCase;

    @PostMapping()
    public ResponseEntity<ResponseCreateTransactionDto> createTransaction(@RequestBody   RequestCreateTransactionDto request){

        var newTransaction=transactionUseCase.create(request.getDescripcion(), request.getLineDtoList().stream().map(LineDtoToDomainMapper::toDomain).toList());
        ResponseCreateTransactionDto response= new ResponseCreateTransactionDto();
        response.setIdTransaction(newTransaction.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel-transaction")
    public ResponseEntity<ResponseCancelTransactionDto> cancelTransaction(@PathVariable Long id){
        var response = new ResponseCancelTransactionDto();
        response.setIdTransaction(id);
        transactionUseCase.cancel(id);
        return ResponseEntity.ok(response);
    }
}
