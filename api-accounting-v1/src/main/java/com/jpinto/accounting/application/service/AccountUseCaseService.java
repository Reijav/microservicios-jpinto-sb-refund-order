package com.jpinto.accounting.application.service;

import com.jpinto.accounting.application.port.in.AccountUseCase;
import com.jpinto.accounting.application.port.out.AccountRepository;
import com.jpinto.accounting.domain.model.Account;


public class AccountUseCaseService implements AccountUseCase {
    private final AccountRepository accountRepository;

    public AccountUseCaseService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getByCode(String code) {
        return accountRepository.getByCode(code);
    }
}
