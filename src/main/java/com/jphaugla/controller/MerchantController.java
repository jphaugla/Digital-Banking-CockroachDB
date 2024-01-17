package com.jphaugla.controller;

import com.jphaugla.domain.Email;
import com.jphaugla.domain.Merchant;
import com.jphaugla.service.MerchantService;
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
@RequestMapping("/api/merchant")
public class MerchantController {

	@Autowired
	private MerchantService merchantService;

	@GetMapping("/getMerchant")
	public ResponseEntity<Merchant> getMerchant(@RequestParam String merchant) {
		log.info("in getMerchant " + merchant);
		return ResponseEntity.ok(merchantService.getMerchantById(merchant));
	}
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteMerchant(@RequestParam String merchant) {
		merchantService.deleteMerchant(merchant);
		return ResponseEntity.ok("Done");
	}
	@PostMapping(value = "/postMerchant", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> postMerchant(@RequestBody Merchant merchant ) throws ParseException {
		merchantService.saveMerchant(merchant);
		return  ResponseEntity.ok(merchant.getName());
	}
}
