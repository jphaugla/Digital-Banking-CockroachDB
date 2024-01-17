package com.jphaugla.controller;

import com.jphaugla.domain.Account;
import com.jphaugla.domain.Email;
import com.jphaugla.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping("/getEmail")
	public ResponseEntity<Email> getEmail(@RequestParam String email) {
		log.info("in getEmail " + email);
		return ResponseEntity.ok(emailService.getEmailById(email));
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteEmail(@RequestParam String email) {
		emailService.deleteEmail(email);
		return ResponseEntity.ok("Done");
	}
	@GetMapping("/getCustomerEmail")
	public ResponseEntity <List<Email>> getCustomerEmail(@RequestParam UUID customerId) {
		log.debug("IN get customerByEmail, email is " + customerId);
		return ResponseEntity.ok(emailService.getAllCustomerEmails(customerId));
	}
	@PostMapping(value = "/postEmail", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postEmail(@RequestBody Email email ) throws ParseException {
		emailService.saveEmail(email);
		return  ResponseEntity.ok(email.getAddress());
	}
}
