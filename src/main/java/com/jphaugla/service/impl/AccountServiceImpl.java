package com.jphaugla.service.impl;

import com.jphaugla.domain.Account;
import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.AccountRepository;
import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account) {
        log.info("accountService.saveAccount");
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID id) {
//		Optional<Account> account = accountRepository.findById(id);
//		if(account.isPresent()) {
//			return account.get();
//		}else {
//			throw new ResourceNotFoundException("Account", "Id", id);
//		}
        return accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Id", id));

    }

    @Override
    public Account updateAccount(Account account, UUID id) {

        // we need to check whether account with given id is exist in DB or not
        Account existingAccount = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Id", id));

        //existingAccount.setEmail(account.getEmail());
        // save existing account to DB
        accountRepository.save(existingAccount);
        return existingAccount;
    }

    @Override
    public void deleteAccount(UUID id) {

        // check whether a account exist in a DB or not
        accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account", "Id", id));
        accountRepository.deleteById(id);
    }
    @Override
    public Account saveSampleAccount(UUID customerId) throws ParseException {
        Date create_date = new SimpleDateFormat("yyyy/MM/dd").parse("2024/01/04");
        log.info("saveSampleAccount with create_date " + create_date.getTime());
        Timestamp create_timestamp = new Timestamp((create_date.getTime()));
        Date open_date = new SimpleDateFormat("yyyy/MM/dd").parse("2020/01/04");
        log.info("saveSampleAccount with open_date " + open_date.getTime());
        Timestamp open_timestamp = new Timestamp((create_date.getTime()));
        Account account = new Account("testAccount", customerId,
                "credit", "teller", "active",
                "ccnumber666655", open_timestamp,
                null, null, "jason", create_timestamp);
        accountRepository.save(account);
        return account;
    }

}
