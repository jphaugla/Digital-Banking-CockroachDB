package com.jphaugla.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphaugla.domain.Account;
import com.jphaugla.domain.Merchant;
import com.jphaugla.domain.Transaction;
import com.jphaugla.domain.TransactionReturn;
import com.jphaugla.exception.InvalidValueException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.MerchantRepository;
import com.jphaugla.repository.TransactionRepository;
import com.jphaugla.repository.TransactionReturnRepository;
import com.jphaugla.repository.TransactionStatusInterface;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.DataGeneratorService;
import com.jphaugla.service.TopicProducerSchema;
import com.jphaugla.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.jphaugla.util.Constants.*;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {
 
    @Autowired
    private TransactionRepository transactionRepository;
    @Value("${app.region}")
    private String source_region;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private TransactionReturnRepository transactionReturnRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    DataGeneratorService dataGeneratorService;
    @Autowired
    private TopicProducerSchema topicProducerSchema;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${topic.name.transaction}")
    private String transactionTopic;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        super();
        this.transactionRepository = transactionRepository;
    }
    @Override
    public void generateData(Integer noOfCustomers, Integer noOfTransactions, Integer noOfDays, String keySuffix, Boolean doKafka)
            throws ExecutionException, JsonProcessingException {

        List<Account> accounts = dataGeneratorService.createCustomerAccount(noOfCustomers, keySuffix);
        log.info("after accounts");
        // date = new DateTime().minusDays(noOfDays).withTimeAtStartOfDay();
        DataGeneratorServiceImpl.Timer transTimer = new DataGeneratorServiceImpl.Timer();

        int totalTransactions = noOfTransactions * noOfDays;

        log.info("Writing " + totalTransactions + " transactions for " + noOfCustomers
                + " customers. suffix is " + keySuffix);
        int account_size = accounts.size();
        int transactionsPerAccount = noOfDays * noOfTransactions / account_size;
        log.info("number of accounts generated is " + account_size + " transactionsPerAccount "
                + transactionsPerAccount);
        List<Merchant> merchants = dataGeneratorService.createMerchantList();
        List<TransactionReturn> transactionReturns = dataGeneratorService.createTransactionReturnList();
        merchantRepository.saveAll(merchants);
        log.info("completed merchant next is transactionReturn");
        transactionReturnRepository.saveAll(transactionReturns);
        int transactionIndex = 0;
        List<Transaction> transactionList = new ArrayList<>();
        for (Account account : accounts) {
            log.info("writing account " + account.getAccountNo());
            for (int i = 0; i < transactionsPerAccount; i++) {
                transactionIndex++;
                Transaction randomTransaction = dataGeneratorService.createRandomTransaction(noOfDays, transactionIndex, account, keySuffix,
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
    public Transaction saveTransaction(Transaction transaction) throws JsonProcessingException {
        log.info("transactionService.saveTransaction");
        transaction.setCurrentTime(source_region);
        String jsonStr = objectMapper.writeValueAsString(transaction);
        log.info("object contents: " + jsonStr);
        return transactionRepository.save(transaction);
    }
    public void writeTransactionKafka(Transaction randomTransaction) throws JsonProcessingException {
       // log.info("writeTransactionKafka started");
       //  UUID key = UUID.randomUUID();
        //  must set the Id of the transaction
        randomTransaction.setCurrentTime(source_region);
        UUID key=randomTransaction.getId();
        topicProducerSchema.send(transactionTopic, key.toString(), randomTransaction);

    }

    @Override
    public Transaction getTransactionById(UUID transaction) throws NotFoundException {
//		Optional<Transaction> transaction = transactionRepository.findById(id);
//		if(transaction.isPresent()) {
//			return transaction.get();
//		}else {
//			throw new ResourceNotFoundException("Transaction", "Id", id);
//		}
        log.info("in getTransactionById with UUID" + transaction.toString());
        return transactionRepository.findById(transaction).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_NOT_FOUND, transaction)));

    }

    @Override
    public void deleteTransaction(UUID id) throws NotFoundException {
        transactionRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_NOT_FOUND, id)));
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
                "Grocery Stores", "MN", "US", source_region);
        log.info("before save merchant");
        merchantRepository.save(merchant);
        List<String> tags = Arrays.asList("Groceries","Necesity");

        Transaction transaction = new Transaction(accountId,
                "Debit", merchant.getName() + ":" + "acct01", "referenceKeyType",
                "referenceKeyValue", 323.23, 323.22, "1631",
                "Test Transaction", null, null,
                null, "POSTED", null, null, "ATM665", null);
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
    public List<Transaction> getMerchantCategoryTransactions(String merchantCategory, UUID account, Date startDate, Date endDate) throws NotFoundException {
        return transactionRepository.findByMerchantCategoryAndAccountIdAndPostingDateBetween(merchantCategory,
                account, startDate, endDate).orElseThrow(() ->new NotFoundException(String.format(ERR_NO_TRANSACTIONS_FOUND_FOR,
                "merchantCategory: " + merchantCategory + " account:" + account.toString() + " start date:" + startDate.toString()
                        + " end date:" + endDate.toString())));
    }

    @Override
    public List<Transaction> getMerchantTransactions(String merchant, UUID account, Date startDate, Date endDate)
            throws NotFoundException {
        return transactionRepository.findByMerchantAndAccountIdAndPostingDateBetween(merchant,
                account, startDate, endDate).orElseThrow(() ->new NotFoundException(String.format(ERR_NO_TRANSACTIONS_FOUND_FOR,
                "merchant: " + merchant + " account:" + account.toString() + " start date:" + startDate.toString()
                        + " end date:" + endDate.toString())));
    }

    @Override
    public List<TransactionStatusInterface> transactionStatusReport() {
        return transactionRepository.countByStatusInterface();
    }

    @Override
    public List<Transaction> getCreditCardTransactions(String creditCard, Date startDate, Date endDate) throws NotFoundException {
        return transactionRepository.findByCreditCardAndPostingDateBetween(creditCard, startDate, endDate).
                orElseThrow(() ->new NotFoundException(String.format(ERR_NO_TRANSACTIONS_FOUND_FOR,
                "credit card: " + creditCard + " start date:" + startDate.toString() + " end date:" + endDate.toString())));
    }

    @Override
    public List<Transaction> getAccountTransactions(UUID accountId, Date startDate, Date endDate) {
        return transactionRepository.findByAccountIdAndPostingDateBetween(accountId, startDate, endDate);
    }

    @Override
    public List<String> addTag(UUID transactionId, String tag) throws NotFoundException, InvalidValueException, JsonProcessingException {
        log.info("transServiceImpl addTab operation tag: " + tag);
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_NOT_FOUND, transactionId)));
        List<String> existingTags = transaction.getTransactionTags();
        if(existingTags != null && !existingTags.isEmpty()) {
            log.info("current tag " + existingTags.toString());
            existingTags.add(tag);
            transaction.setTransactionTags(existingTags);
        } else {
            log.info("current tag is empty");
            List<String> newTag = new ArrayList<String>();
            newTag.add(tag);
            transaction.setTransactionTags(newTag);
        }
        saveTransaction(transaction);
        return transaction.getTransactionTags();
    }
    @Override
    public List<String> deleteTag(UUID transactionId, String tag) throws NotFoundException, JsonProcessingException {
        log.info("transServiceImpl delete Tag operation: tag:" + tag);
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_NOT_FOUND, transactionId)));
        List<String> existingTags = transaction.getTransactionTags();
        if(existingTags != null && !existingTags.isEmpty()) {
            log.info("current tag " + existingTags.toString());
            existingTags.remove(tag);
        } else {
            log.info("current tag is empty");
                throw new NotFoundException(String.format(ERR_TAG_NOT_FOUND,
                        transactionId.toString()));
        }
        saveTransaction(transaction);
        return transaction.getTransactionTags();
    }

    @Override
    public List<String> getTagList(UUID transactionId) throws NotFoundException {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                new NotFoundException(String.format(ERR_TRANSACTION_NOT_FOUND, transactionId)));
        return transaction.getTransactionTags();
    }

    @Override
    public List<Transaction> getTaggedTransactions(UUID accountId, String tag) throws NotFoundException {
        List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionTags(accountId, tag).orElseThrow(() ->
                new NotFoundException(String.format(ERR_NO_TRANSACTIONS_FOUND_FOR,
                        "accountId: " + accountId.toString() + " tag:" + tag)));
        return transactions;
    }

    @Override
    public List<Transaction> mostRecentTransactions(UUID accountId) {
        return transactionRepository.findByAccountIdAndPostingDateBeforeAndLimit(accountId);
    }

    @Override
    public List<Transaction> getTransactionsByStatus(String statusToChange, Integer numberOfTransactions) {
        return transactionRepository.findByStatus(statusToChange, Limit.of(numberOfTransactions));
    }


}
