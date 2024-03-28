package com.jphaugla.service.impl;

import com.jphaugla.domain.Account;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.AccountRepository;
import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.AccountService;
import com.jphaugla.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Constants.ERR_ACCOUNT_NOT_FOUND;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Value("${app.region}")
    private String source_region;

    public AccountServiceImpl(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account) {
        log.info("accountService.saveAccount");
        account.setCurrentTime(source_region);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID id)
            throws NotFoundException, InvalidUUIDException {
//		Optional<Account> account = accountRepository.findById(id);
//		if(account.isPresent()) {
//			return account.get();
//		}else {
//			throw new ResourceNotFoundException("Account", "Id", id);
//		}
        return accountRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ERR_ACCOUNT_NOT_FOUND, id.toString())));
    }

    @Override
    public Account updateAccount(Account account, UUID id) throws NotFoundException {

        // we need to check whether account with given id is exist in DB or not
        Account existingAccount = accountRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ERR_ACCOUNT_NOT_FOUND, id.toString())));

        //existingAccount.setEmail(account.getEmail());
        // save existing account to DB
        accountRepository.save(existingAccount);
        return existingAccount;
    }

    @Override
    public void deleteAccount(UUID id)
            throws NotFoundException, InvalidUUIDException {

        // check whether a account exist in a DB or not
        accountRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ERR_ACCOUNT_NOT_FOUND, id.toString())));
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
                null, "jason", "jason", create_timestamp);
        account.setCurrentTime(source_region);
        accountRepository.save(account);
        return account;
    }

}
