package com.jphaugla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Transaction;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.InvalidValueException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.TransactionStatusInterface;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")

public class TransactionController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private TransactionService transactionService;



	//  transaction
	@GetMapping("/save")
	public ResponseEntity<String> saveTransaction(@RequestParam String accountId, @RequestParam Boolean doKafka)
			throws ParseException, JsonProcessingException, InvalidUUIDException {
		log.info("starting save transaction with doKafka:" + doKafka);
		UUID transactionId = transactionService.saveSampleTransaction(toUUID(accountId, ERR_INVALID_ACCOUNT), doKafka);
		return ResponseEntity.ok(transactionId.toString());
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteTransaction(@RequestParam String transactionId)
			throws NotFoundException, InvalidUUIDException {
		transactionService.deleteTransaction(toUUID(transactionId, ERR_INVALID_TRANSACTION));
		return ResponseEntity.ok(transactionId);
	}

	@GetMapping("/get")
	public ResponseEntity<Transaction> getTransaction(@RequestParam String transactionId) throws NotFoundException, InvalidUUIDException {
		log.info("in transaction controller getTransaction");
		Transaction transaction = transactionService.getTransactionById(toUUID(transactionId, ERR_INVALID_TRANSACTION));
		return ResponseEntity.ok(transaction);
	}

	@GetMapping("/generateData")
	public ResponseEntity<String> generateData (@RequestParam Integer noOfCustomers, @RequestParam Integer noOfTransactions,
								@RequestParam Integer noOfDays, @RequestParam String key_suffix,
								@RequestParam Boolean doKafka)
			throws ParseException, ExecutionException, InterruptedException, IllegalAccessException, JsonProcessingException {
        log.info("starting generate data with doKafka=" + doKafka);
		transactionService.generateData(noOfCustomers, noOfTransactions, noOfDays, key_suffix, doKafka);

		return  ResponseEntity.ok("Done");
	}

	@GetMapping("/merchantCategory")

	public ResponseEntity<List<Transaction>> getMerchantCategoryTransactions
			(@RequestParam String merchantCategory, @RequestParam String account,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException, InvalidUUIDException, NotFoundException {
		log.debug("In getMerchantCategoryTransactions merchantCategory=" + merchantCategory + " account=" + account +
				" from=" + startDate + " to=" + endDate);
		return ResponseEntity.ok(transactionService.getMerchantCategoryTransactions(merchantCategory, toUUID(account,ERR_INVALID_ACCOUNT), startDate, endDate));
	}
	@GetMapping("/merchantTransactions")

	public ResponseEntity<List<Transaction>> getMerchantTransactions
			(@RequestParam String merchant, @RequestParam String account,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException, InvalidUUIDException, NotFoundException {
		log.info("In getMerchantTransactions merchant=" + merchant + " account=" + account +
				" from=" + startDate + " to=" + endDate);
		return ResponseEntity.ok(transactionService.getMerchantTransactions(merchant, toUUID(account,ERR_INVALID_ACCOUNT), startDate, endDate));
	}

	@GetMapping ("/transactionStatusReport")

	public ResponseEntity<List<TransactionStatusInterface>>  transactionStatusReport () {
		return ResponseEntity.ok(transactionService.transactionStatusReport());

	}

	@PutMapping("/statusChange")

	public ResponseEntity<String> generateStatusChangeTransactions(@RequestParam String targetStatus
	, @RequestParam Integer numberOfTransactions)
			throws ParseException, IllegalAccessException, ExecutionException, InterruptedException, InvalidValueException, JsonProcessingException {
		 log.info("generateStatusChangeTransactions transactionStatus=" + targetStatus);
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		 String statusToChange;
		 if (targetStatus.equals("POSTED")) {
			 statusToChange = "SETTLED";
		 } else if (targetStatus.equals("SETTLED")) {
			 statusToChange = "AUTHORIZED";
		 } else {
			 throw new InvalidValueException(String.format(ERR_INVALID_TRANSACTION_STATUS,targetStatus));
		 }
		 List<Transaction> transactions = transactionService.getTransactionsByStatus(statusToChange,
				 numberOfTransactions);
		 for(Transaction transaction : transactions) {
			 transaction.setStatus(targetStatus);
			 if (targetStatus.equals("POSTED") ){
				 transaction.setPostingDate(currentTime);
			 } else {
				 transaction.setSettlementDate(currentTime);
			 }
			 transactionService.saveTransaction(transaction);
		 }

		 return ResponseEntity.ok("Done");

	}


	@GetMapping("/creditCard")

	public ResponseEntity<List<Transaction>>  getCreditCardTransactions
			(@RequestParam String creditCard,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException, NotFoundException {
		log.debug("getCreditCardTransactions creditCard=" + creditCard +
				" startDate=" + startDate + " endDate=" + endDate);
		return ResponseEntity.ok(transactionService.getCreditCardTransactions(creditCard, startDate, endDate));
	}

	@GetMapping("/account")
	public ResponseEntity<List<Transaction>>  getAccountTransactions
			(@RequestParam String accountId,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException, InvalidUUIDException {
		log.debug("getCreditCardTransactions creditCard=" + accountId +
				" startDate=" + startDate + " endDate=" + endDate);
		return ResponseEntity.ok(transactionService.getAccountTransactions(toUUID(accountId,ERR_INVALID_ACCOUNT), startDate, endDate));
	}

	@PutMapping("/addTag")
	public ResponseEntity<List<String>> addTag(@RequestParam String transactionId,
					   @RequestParam String tag, @RequestParam String operation)
			throws InvalidUUIDException, NotFoundException, InvalidValueException, JsonProcessingException {
		log.debug("addTags with transactionId=" + transactionId + " tag is " + tag + " operation is " + operation);
		List<String> validOperations = new ArrayList<String>();
		List<String> returnedTags = new ArrayList<String>();
		validOperations.add("add");
		validOperations.add("delete");
		if(validOperations.contains(operation)) {
			if(operation.equals("delete")) {
				returnedTags=transactionService.deleteTag(toUUID(transactionId, ERR_INVALID_TRANSACTION), tag);
			} else {
				returnedTags=transactionService.addTag(toUUID(transactionId, ERR_INVALID_TRANSACTION), tag);
			}
		} else {
			throw new InvalidValueException(String.format(ERR_INVALID_TAG_OPERATION,operation));
		}
		return ResponseEntity.ok(returnedTags);
	}

	@GetMapping("/getTags")
	public ResponseEntity<List<String>> getTransactionTagList(@RequestParam String transactionId)
			throws InvalidUUIDException, NotFoundException {
		log.debug("getTags with transactionId=" + transactionId);
		return ResponseEntity.ok(transactionService.getTagList(toUUID(transactionId, ERR_INVALID_TRANSACTION)));
	}

	@GetMapping("/getTagged")

	public ResponseEntity<List<Transaction>>  getTaggedTransactions
			(@RequestParam String accountId, @RequestParam String tag)
			throws ParseException, InvalidUUIDException, NotFoundException {
		log.debug("In getTaggedTransactions accountNo=" + accountId + " tag=" + tag );
		return ResponseEntity.ok(transactionService.getTaggedTransactions(toUUID(accountId,ERR_INVALID_ACCOUNT), tag));
	}

	@GetMapping("/mostRecent")
	public ResponseEntity<List<Transaction>> mostRecentTransactions
			(@RequestParam String accountId)
			throws ParseException, InvalidUUIDException {
		log.debug("In mostRecentTransactions accountNo=" + accountId  );
		return ResponseEntity.ok(transactionService.mostRecentTransactions(toUUID(accountId, ERR_INVALID_ACCOUNT)));
	}


}
