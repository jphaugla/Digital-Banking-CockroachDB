package com.jphaugla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Dispute;
import com.jphaugla.domain.Transaction;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.DisputeService;
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
public class DisputeController {

	@Autowired
	private DisputeService disputeService;
	@Autowired
	private TransactionService transactionService;


	@PostMapping(value = "/postDispute", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> postDispute(@RequestBody Dispute dispute ) throws ParseException {
		String disputeId = disputeService.postDispute(dispute);
		return ResponseEntity.ok(disputeId);
	}

	@PutMapping(value = "/putDisputeReason", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Dispute> putDisputeChargeBackReason(@RequestParam UUID disputeId, @RequestParam String reasonCode )  {
		Dispute dispute= disputeService.putDisputeChargeBackReason(disputeId, reasonCode);
		return ResponseEntity.ok(dispute);
	}

	@PutMapping(value = "/acceptDispute", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Dispute> acceptDisputeChargeBack(@RequestParam UUID disputeId)  {
		Dispute dispute=disputeService.acceptDisputeChargeBackReason(disputeId);
		return ResponseEntity.ok(dispute);
	}

	@PutMapping(value = "/resolvedDispute", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Dispute> resolvedDispute(@RequestParam UUID disputeId )  {
		Dispute dispute = disputeService.resolvedDispute(disputeId);
		return ResponseEntity.ok(dispute);
	}
	@GetMapping("/getDispute")
	public ResponseEntity<Dispute> getDispute(@RequestParam UUID disputeId) {
		return ResponseEntity.ok(disputeService.getDisputeById(disputeId));
	}

}
