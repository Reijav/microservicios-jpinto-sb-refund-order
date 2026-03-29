package com.jpinto.accounting.infraestructure.in.controllers;

import com.jpinto.accounting.application.port.out.AccountRepository;
import com.jpinto.accounting.infraestructure.in.dto.AccountDto;
import com.jpinto.accounting.infraestructure.mappers.DomainToAccountDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping("/by-code/{code}")
    private ResponseEntity<AccountDto> getByCode(@PathVariable String code){
        return ResponseEntity.ok(DomainToAccountDtoMapper.toDto(accountRepository.getByCode(code)));
    }
}
