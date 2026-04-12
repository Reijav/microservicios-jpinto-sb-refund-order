package com.jpinto.accounting;

import com.jpinto.accounting.infraestructure.out.entity.AccountEntity;
import com.jpinto.accounting.infraestructure.out.jpa.AccountEntityJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class AccountingApplication {


    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
    }


    @Bean
    @Profile("dev")
    @Transactional
    public CommandLineRunner demo(AccountEntityJpaRepository accountRepository) {
        return (args) -> {

            if(!accountRepository.exists(Example.of( new AccountEntity()))){
                AccountEntity accountGv= new AccountEntity(null, "GV", "Gastos Viático", "GASTO");
                accountRepository.save(accountGv);
                AccountEntity accountBnk= new AccountEntity(null, "BNK", "Bancos", "ACTIVO CORREINTE");
                accountRepository.save(accountBnk);
            }

        };
    }
}
