package com.jphaugla.controller;

import com.jphaugla.domain.Account;
import com.jphaugla.domain.Customer;
import com.jphaugla.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
	public ResponseEntity<Account> saveSampleAccount(@RequestParam UUID customerId) throws ParseException {
		log.info("starting save account");
		Account account = accountService.saveSampleAccount(customerId);
		return ResponseEntity.ok(account);
	}

	@GetMapping("/get")
	public ResponseEntity<Account> getAccount(@RequestParam UUID accountId) {
		return ResponseEntity.ok(accountService.getAccountById(accountId));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteAccount(@RequestParam UUID accountId) {
		accountService.deleteAccount(accountId);
		return ResponseEntity.ok(accountId.toString());
	}
	@PostMapping(value = "/postAccount", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postAccount(@RequestBody Account account ) throws ParseException {
		accountService.saveAccount(account);
		return  ResponseEntity.ok(account.getId().toString());
	}

}
