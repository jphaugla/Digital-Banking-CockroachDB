package com.jphaugla.controller;

import com.jphaugla.domain.TransactionReturn;
import com.jphaugla.service.TransactionReturnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transactionReturn")
public class TransactionReturnController {

	@Autowired
	private TransactionReturnService transactionReturnService;

	@GetMapping("/get")
	public ResponseEntity<TransactionReturn> getTransactionReturn(@RequestParam String transactionReturn) {
		log.debug("IN get transactionReturn, transactionReturn is " + transactionReturn);
		return ResponseEntity.ok(transactionReturnService.getTransactionReturnById(transactionReturn));	
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteTransactionReturn(@RequestParam String transactionReturn) {
		transactionReturnService.deleteTransactionReturn(transactionReturn);
		return ResponseEntity.ok("Done");
	}

	@PostMapping(value = "/post", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postTransactionReturn(@RequestBody TransactionReturn transactionReturn ) throws ParseException {
		transactionReturnService.saveTransactionReturn(transactionReturn);
		return  ResponseEntity.ok(transactionReturn.getReasonCode());
	}
}
