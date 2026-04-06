package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.accounting.AccountingServiceRestClientService;
import com.jpinto.orchestator.client.accounting.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountingService {
    private final AccountingServiceRestClientService accountingServiceRestClientService;

    public ResponseCreateTransactionDto createTransaction(RequestCreateTransactionDto request) {
        return accountingServiceRestClientService.createTransaction(request);
    }

    public ResponseCancelTransactionDto cancelTransaction(RequestCancelTransactionDto requestCancelTransactionDto) {
        return accountingServiceRestClientService.cancelTransaction(requestCancelTransactionDto);
    }

    public AccountDto getAccountByCode(String code) {
        return accountingServiceRestClientService.accountByCode(code);
    }
}
