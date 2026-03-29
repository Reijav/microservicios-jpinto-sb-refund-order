package com.jpinto.accounting.application.service;

import com.jpinto.accounting.application.port.in.TransactionUseCase;
import com.jpinto.accounting.application.port.out.TransactionRepository;
import com.jpinto.accounting.domain.model.Transaction;
import com.jpinto.accounting.domain.model.TransactionLine;

import java.time.LocalDate;
import java.util.List;

public class TransactionUseCaseService implements TransactionUseCase {

    private final TransactionRepository transactionRepository;

    public TransactionUseCaseService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction create(String description, List<TransactionLine> lineDtoList) {
        Transaction transaction= new Transaction(null, description, LocalDate.now(), lineDtoList, 1L);
        return  this.transactionRepository.create(transaction);
    }

    @Override
    public Transaction update(Long idTransaction, List<TransactionLine> lineDtoList) {

        Transaction transaction=  Transaction.builder()
                .id(idTransaction)
                .transactionLineList(lineDtoList)
                .build();
        return this.transactionRepository.update(transaction);
    }

    @Override
    public Transaction cancel(Long idTransaction) {
        return this.transactionRepository.cancel(idTransaction);
    }
}
