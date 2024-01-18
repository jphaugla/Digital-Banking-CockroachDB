package com.jphaugla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Transaction;
import com.jphaugla.exception.NotFoundException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface TransactionService {
    Transaction saveTransaction (Transaction  transaction );
    Transaction  getTransactionById(UUID id) throws NotFoundException;
    void deleteTransaction(UUID id) throws NotFoundException;

    UUID saveSampleTransaction(UUID accountId, Boolean doKafka) throws ParseException, JsonProcessingException;

    void generateData(Integer noOfCustomers, Integer noOfTransactions, Integer noOfDays, String keySuffix, Boolean doKafka) throws ExecutionException, JsonProcessingException;

    List<Transaction> getMerchantCategoryTransactions(String merchantCategory, UUID account, Date startDate, Date endDate) throws NotFoundException;

    List<Transaction> getMerchantTransactions(String merchant, UUID account, Date startDate, Date endDate) throws NotFoundException;

    List<Map<String, Object>> transactionStatusReport();

    List<Transaction> getCreditCardTransactions(String creditCard, Date startDate, Date endDate);

    List<Transaction> getAccountTransactions(String accountNo, Date startDate, Date endDate);

    void addTag(UUID transactionID, String tag, String operation);

    String getTransactionTagList(UUID transactionID);

    List<Transaction> getTaggedTransactions(String accountNo, String tag);

    Transaction mostRecentTransactions(String accountNo);
}
