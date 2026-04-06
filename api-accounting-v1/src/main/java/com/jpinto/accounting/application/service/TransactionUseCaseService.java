package com.jpinto.accounting.application.service;

import com.jpinto.accounting.application.port.in.TransactionUseCase;
import com.jpinto.accounting.application.port.out.TransactionRepository;
import com.jpinto.accounting.domain.model.Transaction;
import com.jpinto.accounting.domain.model.TransactionLine;
import com.jpinto.accounting.producer.cancel.CancelTransactionProducer;
import com.jpinto.accounting.producer.create.CreatedTransactionProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionUseCaseService implements TransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final CreatedTransactionProducer createdTransactionProducer;
    private final CancelTransactionProducer cancelTransactionProducer;

    public TransactionUseCaseService(TransactionRepository transactionRepository,
                                     CreatedTransactionProducer createdTransactionProducer,
                                     CancelTransactionProducer cancelTransactionProducer) {
        this.transactionRepository = transactionRepository;
        this.createdTransactionProducer=createdTransactionProducer;
        this.cancelTransactionProducer=cancelTransactionProducer;
    }


    @Override
    public Transaction create(String orderRefundId, String description, List<TransactionLine> lineDtoList) {
        Transaction transaction= new Transaction(null, description, LocalDate.now(), lineDtoList, 1L);
        transaction=  this.transactionRepository.create(transaction);
        createdTransactionProducer.produce(orderRefundId, transaction.getId());
        return transaction;
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
    public Transaction cancel(String orderRefundId, Long idTransaction) {
        cancelTransactionProducer.produce(orderRefundId, idTransaction);
        return this.transactionRepository.cancel(idTransaction);
    }
}
