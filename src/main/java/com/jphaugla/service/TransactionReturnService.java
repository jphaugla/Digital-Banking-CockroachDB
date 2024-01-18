package com.jphaugla.service;

import com.jphaugla.domain.TransactionReturn;
import com.jphaugla.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface TransactionReturnService {
    TransactionReturn saveTransactionReturn (TransactionReturn  transactionReturn );
    List<TransactionReturn > getAllTransactionReturns();
    TransactionReturn  getTransactionReturnById(String id) throws NotFoundException;
    void deleteTransactionReturn(String id) throws NotFoundException;
}
