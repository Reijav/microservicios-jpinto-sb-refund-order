package com.jpinto.orchestator.client.accounting;

import com.jpinto.orchestator.client.accounting.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountingServiceRestClientService {

    public final  RestClient accountingRestClient;

    public ResponseCreateTransactionDto createTransaction(RequestCreateTransactionDto request){
        log.info("Calling create transaction.");
        return accountingRestClient.post().uri("/transacciones").body(request).retrieve().body(ResponseCreateTransactionDto.class);
    }

    public ResponseCancelTransactionDto cancelTransaction(RequestCancelTransactionDto request){
        log.info("Calling cancel transaction.");
        return accountingRestClient.put().uri("/transacciones/cancel-transaction").body(request).retrieve().body(ResponseCancelTransactionDto.class);
    }


    public AccountDto accountByCode(String code){
        log.info("Calling get account by code");
        return accountingRestClient.get().uri("/accounts/by-code/{code}", code).retrieve().body(AccountDto.class);
    }

}
