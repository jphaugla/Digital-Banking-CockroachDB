package com.jphaugla.service;

import com.jphaugla.domain.Account;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account saveAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(UUID id);
    Account updateAccount(Account account, UUID id);
    void deleteAccount(UUID id);
    Account saveSampleAccount(UUID customerId) throws ParseException;

}
