package com.jphaugla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Transaction;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.TopicProducer;
import com.jphaugla.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
	@RequestMapping("/save")
	public ResponseEntity<String> saveTransaction(@RequestParam UUID accountId, @RequestParam Boolean doKafka) throws ParseException, JsonProcessingException, JsonProcessingException {
		log.info("starting save transaction with doKafka:" + doKafka);
		UUID transactionId = transactionService.saveSampleTransaction(accountId, doKafka);
		return ResponseEntity.ok(transactionId.toString());
	}
	private final TopicProducer topicProducer;

	//  test send message
	@GetMapping (value = "/send")
	public void send() throws ExecutionException, InterruptedException {
		topicProducer.send("Mensagem de teste enviada ao tópico", "mensagem");
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
			throws ParseException {
		log.debug("In getMerchantCategoryTransactions merchantCategory=" + merchantCategory + " account=" + account +
				" from=" + startDate + " to=" + endDate);
		return ResponseEntity.ok(transactionService.getMerchantCategoryTransactions(merchantCategory, account, startDate, endDate));
	}
	@GetMapping("/merchantTransactions")

	public ResponseEntity<List<Transaction>> getMerchantTransactions
			(@RequestParam String merchant, @RequestParam String account,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
				throws ParseException {
		log.info("In getMerchantTransactions merchant=" + merchant + " account=" + account +
				" from=" + startDate + " to=" + endDate);
		return ResponseEntity.ok(transactionService.getMerchantTransactions(merchant, account, startDate, endDate));
	}


	@GetMapping ("/transactionStatusReport")

	public ResponseEntity<List<Map<String, Object>>>  transactionStatusReport () {
		return ResponseEntity.ok(transactionService.transactionStatusReport());

	}

	/*
	@GetMapping("/statusChangeTransactions")

	public AggregateResults<String> generateStatusChangeTransactions(@RequestParam String transactionStatus)
			throws ParseException, IllegalAccessException, ExecutionException, InterruptedException {
		 log.info("generateStatusChangeTransactions transactionStatus=" + transactionStatus);
		 AggregateResults<String> changeReport = new AggregateResults<>();

		 changeReport.addAll(transactionStatusReport());
		 bankService.transactionStatusChange(transactionStatus);
		 changeReport.addAll(transactionStatusReport());

		 return changeReport;

	}
	*/

	@GetMapping("/creditCard")

	public ResponseEntity<List<Transaction>>  getCreditCardTransactions
			(@RequestParam String creditCard,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException {
		log.debug("getCreditCardTransactions creditCard=" + creditCard +
				" startDate=" + startDate + " endDate=" + endDate);
		return ResponseEntity.ok(transactionService.getCreditCardTransactions(creditCard, startDate, endDate));
	}

	@GetMapping("/account")

	public ResponseEntity<List<Transaction>>  getAccountTransactions
			(@RequestParam String accountNo,
			 @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			 @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
			throws ParseException {
		log.debug("getCreditCardTransactions creditCard=" + accountNo +
				" startDate=" + startDate + " endDate=" + endDate);
		return ResponseEntity.ok(transactionService.getAccountTransactions(accountNo, startDate, endDate));

	}

	@GetMapping("/addTag")

	public ResponseEntity<String> addTag(@RequestParam UUID transactionID,
					   @RequestParam String tag, @RequestParam String operation) {
		log.debug("addTags with transactionID=" + transactionID + " tag is " + tag + " operation is " + operation);
		transactionService.addTag(transactionID, tag, operation);
		return ResponseEntity.ok("Done");
	}

	@GetMapping("/getTags")
	public ResponseEntity <String> getTransactionTagList(@RequestParam String transactionID) {
		log.debug("getTags with transactionID=" + transactionID);
		return ResponseEntity.ok(transactionService.getTransactionTagList(transactionID));
	}

	@GetMapping("/getTaggedTransactions")

	public ResponseEntity<List<Transaction>>  getTaggedTransactions
			(@RequestParam String accountNo, @RequestParam String tag)
			throws ParseException {
		log.debug("In getTaggedTransactions accountNo=" + accountNo + " tag=" + tag );
		return ResponseEntity.ok(transactionService.getTaggedTransactions(accountNo, tag));
	}
	@GetMapping("/get")
	public ResponseEntity<Transaction> getTransaction(@RequestParam UUID transactionID) {
		Transaction transaction = transactionService.getTransactionById(transactionID);
		return ResponseEntity.ok(transaction);
	}
	@GetMapping("/mostRecentTransactions")

	public ResponseEntity<Transaction> mostRecentTransactions
			(@RequestParam String accountNo)
			throws ParseException {
		log.debug("In mostRecentTransactions accountNo=" + accountNo  );
		return ResponseEntity.ok(transactionService.mostRecentTransactions(accountNo));
	}


}
