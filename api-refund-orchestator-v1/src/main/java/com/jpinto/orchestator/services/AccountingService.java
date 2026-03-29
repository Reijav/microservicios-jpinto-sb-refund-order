package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.accounting.AccountingServiceRestClientService;
import com.jpinto.orchestator.client.accounting.dto.AccountDto;
import com.jpinto.orchestator.client.accounting.dto.RequestCreateTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.ResponseCancelTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.ResponseCreateTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AccountingServiceRestClientService accountingServiceRestClientService;

    public ResponseCreateTransactionDto createTransaction(RequestCreateTransactionDto request) {
        return accountingServiceRestClientService.createTransaction(request);
    }

    public ResponseCancelTransactionDto cancelTransaction(Long id) {
        return accountingServiceRestClientService.cancelTransaction(id);
    }

    public AccountDto getAccountByCode(String code) {
        return accountingServiceRestClientService.accountByCode(code);
    }
}
