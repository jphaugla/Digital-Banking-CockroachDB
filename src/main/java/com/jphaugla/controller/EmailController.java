package com.jphaugla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Account;
import com.jphaugla.domain.Email;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.ERR_CUSTOMER_EMAIL_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping("/getEmail")
	public ResponseEntity<Email> getEmail(@RequestParam String email) throws NotFoundException {
		log.info("in getEmail " + email);
		return ResponseEntity.ok(emailService.getEmailById(email));
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteEmail(@RequestParam String email) throws NotFoundException {
		emailService.deleteEmail(email);
		return ResponseEntity.ok(email);
	}
	@GetMapping("/getCustomerEmail")
	public ResponseEntity <List<Email>> getCustomerEmail(@RequestParam String customerId) throws NotFoundException, InvalidUUIDException {
		log.debug("IN get customerEmail, email is " + customerId);
		return ResponseEntity.ok(emailService.getAllCustomerEmails(toUUID(customerId,ERR_CUSTOMER_EMAIL_NOT_FOUND)));
	}
	@PostMapping(value = "/postEmail", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postEmail(@RequestBody Email email ) throws ParseException {
		emailService.saveEmail(email);
		return  ResponseEntity.ok(email.getAddress());
	}
	@PostMapping(value = "/saveKafka", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> saveKafkaEmail(@RequestBody Email email )
			throws ParseException, JsonProcessingException {
		log.info("started saveKafka" + email.toString());
		emailService.saveEmailKafka(email);
		return  ResponseEntity.ok(email.getAddress());
	}
}
