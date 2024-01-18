package com.jphaugla.controller;

import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.ERR_CUSTOMER_PHONE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/phone")
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@GetMapping("/getPhone")

	public ResponseEntity<Phone> getPhone(@RequestParam String phone) throws NotFoundException {
		log.debug("IN get phone, phone is " + phone);
		return ResponseEntity.ok(phoneService.getPhoneById(phone));	
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePhone(@RequestParam String phone) throws NotFoundException {
		phoneService.deletePhone(phone);
		return ResponseEntity.ok(phone);
	}
	@GetMapping("/getCustomerPhone")
	public ResponseEntity <List<Phone>> getCustomerPhone(@RequestParam String customerId) throws InvalidUUIDException {
		log.debug("IN get customerByPhone, phone is " + customerId);
		return ResponseEntity.ok(phoneService.getAllCustomerPhones(toUUID(customerId,ERR_CUSTOMER_PHONE_NOT_FOUND)));
	}
	@PostMapping(value = "/postPhone", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postPhone(@RequestBody Phone phone ) throws ParseException {
		phoneService.savePhone(phone);
		return  ResponseEntity.ok(phone.getNumber());
	}
}
