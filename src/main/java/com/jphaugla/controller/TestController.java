package com.jphaugla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Test;
import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.jphaugla.util.Common.toUUID;
import static com.jphaugla.util.Constants.ERR_CUSTOMER_EMAIL_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private TestService testService;


	@PostMapping(value = "/saveKafka", consumes = "application/json", produces = "application/json")
	public  ResponseEntity<String> saveKafkaEmail(@RequestBody Test test )
			throws ParseException, JsonProcessingException {
		log.info("started saveKafka" + test.toString());
		testService.saveTestKafka(test);
		return  ResponseEntity.ok(test.getCol1());
	}
}
