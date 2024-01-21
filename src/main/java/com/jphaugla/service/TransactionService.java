package com.jphaugla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Transaction;
import com.jphaugla.exception.InvalidValueException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.TransactionStatusInterface;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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

    List<TransactionStatusInterface> transactionStatusReport();

    List<Transaction> getCreditCardTransactions(String creditCard, Date startDate, Date endDate) throws NotFoundException;

    List<Transaction> getAccountTransactions(UUID accountId, Date startDate, Date endDate);

    List<String> addTag(UUID transactionID, String tag) throws NotFoundException, InvalidValueException;

    List<String> getTagList(UUID transactionID) throws NotFoundException;

    List<Transaction> getTaggedTransactions(UUID accountId, String tag) throws NotFoundException;

    List<Transaction> mostRecentTransactions(UUID accountId);

    List<Transaction> getTransactionsByStatus(String statusToChange, Integer numberOfTransactions);

    List<String> deleteTag(UUID uuid, String tag)throws NotFoundException;
}
