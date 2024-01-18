package com.jphaugla.service;

import com.jphaugla.domain.Account;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account saveAccount(Account account);
    List<Account> getAllAccounts();
    Account getAccountById(UUID id) throws NotFoundException, InvalidUUIDException;
    Account updateAccount(Account account, UUID id) throws NotFoundException;
    void deleteAccount(UUID id) throws NotFoundException, InvalidUUIDException;
    Account saveSampleAccount(UUID customerId) throws ParseException;

}
