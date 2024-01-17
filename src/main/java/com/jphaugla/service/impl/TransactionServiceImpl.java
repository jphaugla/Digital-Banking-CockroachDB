package com.jphaugla.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphaugla.data.BankGenerator;
import com.jphaugla.domain.Account;
import com.jphaugla.domain.Merchant;
import com.jphaugla.domain.Transaction;
import com.jphaugla.domain.TransactionReturn;
import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.CustomerRepository;
import com.jphaugla.repository.MerchantRepository;
import com.jphaugla.repository.TransactionRepository;
import com.jphaugla.repository.TransactionReturnRepository;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.TopicProducer;
import com.jphaugla.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
 
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private TransactionReturnRepository transactionReturnRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    private TopicProducer topicProducer;
    @Autowired
    ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        log.info("transactionService.saveTransaction");
        return transactionRepository.save(transaction);
    }
    private void writeTransactionKafka(Transaction randomTransaction) throws JsonProcessingException {
        try {
            String jsonStr = objectMapper.writeValueAsString(randomTransaction);
            UUID key = randomTransaction.getId();
            topicProducer.send(jsonStr, String.valueOf(randomTransaction.getId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Transaction getTransactionById(UUID transaction) {
//		Optional<Transaction> transaction = transactionRepository.findById(id);
//		if(transaction.isPresent()) {
//			return transaction.get();
//		}else {
//			throw new ResourceNotFoundException("Transaction", "Id", id);
//		}
        return transactionRepository.findById(transaction).orElseThrow(() ->
                new ResourceNotFoundException("Transaction", "Id", transaction));

    }

    @Override
    public void deleteTransaction(UUID id) {
        transactionRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Transaction", "Id", id));
    }

    @Override
    public UUID saveSampleTransaction(UUID accountId, Boolean doKafka) throws ParseException, JsonProcessingException {
        Date settle_date = new SimpleDateFormat("yyyy/MM/dd").
                parse("2021/07/28");
        Timestamp settle_timestamp = new Timestamp((settle_date.getTime()));

        Date post_date = new SimpleDateFormat("yyyy/MM/dd").parse("2021/07/28");
        Timestamp post_timestamp = new Timestamp((post_date.getTime()));

        Date init_date = new SimpleDateFormat("yyyy/MM/dd").parse("2021/07/27");
        Timestamp init_timestamp = new Timestamp((init_date.getTime()));
        Merchant merchant = new Merchant("Cub Foods", "5411",
                "Grocery Stores", "MN", "US");
        log.info("before save merchant");
        merchantRepository.save(merchant);

        Transaction transaction = new Transaction(accountId,
                "Debit", merchant.getName() + ":" + "acct01", "referenceKeyType",
                "referenceKeyValue", 323.23, 323.22, "1631",
                "Test Transaction", init_timestamp, settle_timestamp,
                post_timestamp, "POSTED", null, null, "ATM665", "Outdoor");
        log.info("before save transaction");
        if (doKafka) {
            try {
                writeTransactionKafka(transaction);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            saveTransaction(transaction);
        }
        return transaction.getId();
    }

    @Override
    public void generateData(Integer noOfCustomers, Integer noOfTransactions, Integer noOfDays, String keySuffix, Boolean doKafka) throws ExecutionException, JsonProcessingException {

        List<Account> accounts = customerService.createCustomerAccount(noOfCustomers, keySuffix);
        log.info("after accounts");
        BankGenerator.date = new DateTime().minusDays(noOfDays).withTimeAtStartOfDay();
        BankGenerator.Timer transTimer = new BankGenerator.Timer();

        int totalTransactions = noOfTransactions * noOfDays;

        log.info("Writing " + totalTransactions + " transactions for " + noOfCustomers
                + " customers. suffix is " + keySuffix);
        int account_size = accounts.size();
        int transactionsPerAccount = noOfDays * noOfTransactions / account_size;
        log.info("number of accounts generated is " + account_size + " transactionsPerAccount "
                + transactionsPerAccount);
        List<Merchant> merchants = BankGenerator.createMerchantList();
        List<TransactionReturn> transactionReturns = BankGenerator.createTransactionReturnList();
        merchantRepository.saveAll(merchants);
        log.info("completed merchant next is transactionReturn");
        transactionReturnRepository.saveAll(transactionReturns);
        int transactionIndex = 0;
        List<Transaction> transactionList = new ArrayList<>();
        for (Account account : accounts) {
            log.info("writing account " + account.getAccountNo());
            for (int i = 0; i < transactionsPerAccount; i++) {
                transactionIndex++;
                Transaction randomTransaction = BankGenerator.createRandomTransaction(noOfDays, transactionIndex, account, keySuffix,
                        merchants, transactionReturns);
                if (doKafka)
                    writeTransactionKafka(randomTransaction);
                else
                    saveTransaction(randomTransaction);
            }
        }
        transTimer.end();
        log.info("Finished writing " + totalTransactions + " created in " +
                transTimer.getTimeTakenSeconds() + " seconds.");
    }

    @Override
    public List<Transaction> getMerchantCategoryTransactions(String merchantCategory, String account, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Transaction> getMerchantTransactions(String merchant, String account, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Map<String, Object>> transactionStatusReport() {
        return null;
    }

    @Override
    public List<Transaction> getCreditCardTransactions(String creditCard, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Transaction> getAccountTransactions(String accountNo, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public void addTag(UUID transactionID, String tag, String operation) {

    }

    @Override
    public String getTransactionTagList(String transactionID) {
        return null;
    }

    @Override
    public List<Transaction> getTaggedTransactions(String accountNo, String tag) {
        return null;
    }

    @Override
    public Transaction mostRecentTransactions(String accountNo) {
        return null;
    }


}
