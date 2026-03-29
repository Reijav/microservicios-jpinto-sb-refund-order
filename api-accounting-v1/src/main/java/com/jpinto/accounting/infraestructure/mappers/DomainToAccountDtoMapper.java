package com.jpinto.accounting.infraestructure.mappers;

import com.jpinto.accounting.domain.model.Account;
import com.jpinto.accounting.infraestructure.in.dto.AccountDto;

public class DomainToAccountDtoMapper {
    public static AccountDto toDto(Account account){
        return AccountDto.builder()
                .code(account.getCode())
                .id(account.getId())
                .name(account.getName())
                .type(account.getType())
                .build();
    }

}
