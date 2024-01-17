package com.jphaugla.service;

import com.jphaugla.domain.TransactionReturn;

import java.util.List;
import java.util.UUID;

public interface TransactionReturnService {
    TransactionReturn saveTransactionReturn (TransactionReturn  transactionReturn );
    List<TransactionReturn > getAllTransactionReturns();
    TransactionReturn  getTransactionReturnById(String id);
    void deleteTransactionReturn(String id);
}
