package com.jpinto.accounting.infraestructure.in.config;

import com.jpinto.accounting.application.port.in.TransactionUseCase;
import com.jpinto.accounting.application.port.out.TransactionRepository;
import com.jpinto.accounting.application.service.TransactionUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    @Bean
    public TransactionUseCase transactionUseCase(TransactionRepository transactionRepository){
        return new TransactionUseCaseService(transactionRepository) ;
    }
}
