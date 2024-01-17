package com.jphaugla.controller;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import com.jphaugla.domain.Customer;
import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.EmailService;
import com.jphaugla.service.PhoneService;
import com.jphaugla.service.TopicProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private EmailService emailService;

	// customer
	@GetMapping("/save")
	public ResponseEntity<String> saveCustomer() throws ParseException {
		log.info("in controller save customer");
		Customer customer = customerService.saveSampleCustomer();
		return ResponseEntity.ok(customer.getId().toString());
	}
	@GetMapping("/email")
	public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email_string) {
		log.debug("IN get customerByEmail, email is " + email_string);
		Email email = emailService.getEmailById(email_string);
		return ResponseEntity.ok(customerService.getCustomerById(email.getCustomerId()));
	}
	@GetMapping("/phone")
	public ResponseEntity<Customer> getCustomerByPhone(@RequestParam String phoneString) {
		log.debug("In get customerByPhone with phone as " + phoneString);
		Phone phone = phoneService.getPhoneById(phoneString);
		return ResponseEntity.ok(customerService.getCustomerById(phone.getCustomerId()));
	}

	@GetMapping("/get")
	public ResponseEntity<Customer> getCustomer(@RequestParam UUID customerId) {
		return ResponseEntity.ok(customerService.getCustomerById(customerId));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCustomer(@RequestParam UUID customerId) {
		customerService.deleteCustomer(customerId);
		emailService.deleteCustomerEmails(customerId);
		phoneService.deleteCustomerPhones(customerId);
		return ResponseEntity.ok(customerId.toString());
	}

	@PostMapping(value = "/postCustomer", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postCustomer(@RequestBody Customer customer ) throws ParseException {
		customerService.saveCustomer(customer);
		return  ResponseEntity.ok(customer.getId().toString());
	}

	@GetMapping("/getStateCity")
	public ResponseEntity<List<Customer>> getCustomerByStateCity(@RequestParam String state, @RequestParam String city) {
		log.debug("IN get customerByState with state as " + state + " and city=" + city);
		return ResponseEntity.ok(customerService.getCustomerByStateCity(state, city));
	}

	@GetMapping("/getZipLastname")
	public ResponseEntity<List<Customer>> getCustomerByZipLast(@RequestParam String zipcode, @RequestParam String lastname) {
		log.debug("IN get customerByState with zip as " + zipcode + " and lastname=" + lastname);
		return ResponseEntity.ok(customerService.getCustomerByZipLast(zipcode, lastname));
	}
}
