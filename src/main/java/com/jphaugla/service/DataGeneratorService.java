package com.jphaugla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.*;
import com.jphaugla.exception.NotFoundException;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface DataGeneratorService {


    List<Email> createEmail(Customer customer);

    List<Phone> createPhone(Customer customer);

    Customer createRandomCustomer(String key_suffix);

    List<Merchant> createMerchantList();

    List<String> createMerchantName();

    List<TransactionReturn> createTransactionReturnList();

    List<Account> createRandomAccountsForCustomer(Customer customer, String key_suffix);

    Transaction createRandomTransaction(int noOfDays, Integer idx, Account account,
                                        String key_suffix, List<Merchant> merchants,
                                        List<TransactionReturn> transactionReturns);

    List<Account> createCustomerAccount(int noOfCustomers, String key_suffix) throws ExecutionException;

    void createItemsAndAmount(int noOfItems, Transaction transaction);
}
