package com.jphaugla.controller;

import com.jphaugla.domain.Account;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.ERR_INVALID_ACCOUNT;
import static com.jphaugla.util.Constants.ERR_INVALID_CUSTOMER;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;


	//  transaction
	@GetMapping("/save")
	public ResponseEntity<Account> saveSampleAccount(@RequestParam String customerId)
			throws ParseException, InvalidUUIDException {
		log.info("starting save account");
		Account account = accountService.saveSampleAccount(toUUID(customerId, ERR_INVALID_CUSTOMER));
		return ResponseEntity.ok(account);
	}

	@GetMapping("/get")
	public ResponseEntity<Account> getAccount(@RequestParam String accountId)
			throws NotFoundException, InvalidUUIDException {
		return ResponseEntity.ok(accountService.getAccountById(toUUID(accountId, ERR_INVALID_ACCOUNT)));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAccount(@RequestParam String accountId)
			throws NotFoundException, InvalidUUIDException {
		accountService.deleteAccount(toUUID(accountId,ERR_INVALID_ACCOUNT));
		return ResponseEntity.ok(accountId);
	}
	@PostMapping(value = "/postAccount", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postAccount(@RequestBody Account account ) throws ParseException {
		accountService.saveAccount(account);
		return  ResponseEntity.ok(account.getId().toString());
	}

}
