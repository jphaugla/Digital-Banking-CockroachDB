package com.jphaugla.controller;


import com.jphaugla.domain.Customer;
import com.jphaugla.domain.Dispute;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.service.DisputeService;
import com.jphaugla.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.ERR_INVALID_DISPUTE;

@Slf4j
@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dispute")
public class DisputeController {

	@Autowired
	private DisputeService disputeService;
	@Autowired
	private TransactionService transactionService;




	@PutMapping(value = "/putReason", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Dispute> putDisputeChargeBackReason(@RequestParam String disputeId, @RequestParam String reasonCode )
			throws NotFoundException, InvalidUUIDException {
		Dispute dispute= disputeService.putDisputeChargeBackReason(toUUID(disputeId,ERR_INVALID_DISPUTE), reasonCode);
		return ResponseEntity.ok(dispute);
	}

	@PutMapping(value = "/accept", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Dispute> acceptDisputeChargeBack(@RequestParam String disputeId) throws InvalidUUIDException, NotFoundException {
		Dispute dispute=disputeService.acceptDisputeChargeBackReason(toUUID(disputeId,ERR_INVALID_DISPUTE));
		return ResponseEntity.ok(dispute);
	}

	@PutMapping(value = "/resolve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<Dispute> resolvedDispute(@RequestParam String disputeId ) throws InvalidUUIDException, NotFoundException {
		Dispute dispute = disputeService.resolvedDispute(toUUID(disputeId,ERR_INVALID_DISPUTE));
		return ResponseEntity.ok(dispute);
	}
	@GetMapping("/get")
	public ResponseEntity<Dispute> getDispute(@RequestParam String disputeId) throws InvalidUUIDException, NotFoundException {
		return ResponseEntity.ok(disputeService.getDisputeById(toUUID(disputeId,ERR_INVALID_DISPUTE)));
	}
	@PostMapping(value = "/postDispute", consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = "application/json")
	@ResponseBody
	public  ResponseEntity<String> postDispute(@RequestBody final Dispute dispute )
			throws ParseException, NotFoundException {
		log.info("in postDispute with dispute "+ dispute.toString());
		disputeService.postDispute(dispute);
		return  ResponseEntity.ok(dispute.getId().toString());
	}

}
