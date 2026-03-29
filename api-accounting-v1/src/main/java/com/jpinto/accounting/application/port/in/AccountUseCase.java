package com.jpinto.accounting.application.port.in;

import com.jpinto.accounting.domain.model.Account;

public interface AccountUseCase {
    public Account getByCode(String code);
}
