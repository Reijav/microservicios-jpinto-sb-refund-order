package com.jpinto.orchestator.client.accounting;

import com.jpinto.orchestator.client.accounting.dto.AccountDto;
import com.jpinto.orchestator.client.accounting.dto.RequestCreateTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.ResponseCancelTransactionDto;
import com.jpinto.orchestator.client.accounting.dto.ResponseCreateTransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AccountingServiceRestClientService {

    public final  RestClient accountingRestClient;

    public ResponseCreateTransactionDto createTransaction(RequestCreateTransactionDto request){

        return accountingRestClient.post().uri("/transacciones").body(request).retrieve().body(ResponseCreateTransactionDto.class);
    }

    public ResponseCancelTransactionDto cancelTransaction( Long id){
        return accountingRestClient.put().uri("/transacciones/{id}/cancel-transaction", id).retrieve().body(ResponseCancelTransactionDto.class);
    }


    public AccountDto accountByCode(String code){
        return accountingRestClient.get().uri("/accounts/by-code/{code}", code).retrieve().body(AccountDto.class);
    }

}
