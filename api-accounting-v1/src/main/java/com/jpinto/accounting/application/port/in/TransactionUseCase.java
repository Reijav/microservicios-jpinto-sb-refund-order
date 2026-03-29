package com.jpinto.accounting.application.port.in;

import com.jpinto.accounting.domain.model.Transaction;
import com.jpinto.accounting.domain.model.TransactionLine;

import java.util.List;

public interface TransactionUseCase {
    Transaction create(String description, List<TransactionLine> lineDtoList);
    Transaction update(Long idTransaction,  List<TransactionLine> lineDtoList);
    Transaction cancel(Long idTransaction);
}
