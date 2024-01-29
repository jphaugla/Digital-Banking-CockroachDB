package com.jphaugla.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Test;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.TestRepository;
import com.jphaugla.service.TestService;
import com.jphaugla.service.TopicProducerSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Constants.ERR_EMAIL_NOT_FOUND;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
 
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TopicProducerSchema topicProducerSchema;
    @Value("${topic.name.test}")
    private String testTopic;


    public TestServiceImpl(TestRepository testRepository) {
        super();
        this.testRepository = testRepository;
    }

    @Override
    public Test saveTest(Test test) {
        log.info("testService.saveTest");
        return testRepository.save(test);
    }

    @Override
    public Test saveTestKafka(Test test) throws JsonProcessingException {
        log.info("testService.saveTest Kafka");
       // topicProducerTest.send(test);
        test.setCurrentTime();
        UUID key = UUID.randomUUID();
        test.setCustomer(key);
        topicProducerSchema.send(testTopic, test.getCol1(), test);
        log.info("before return from saveTestKafka");
        return test;
    }

    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public Test getTestById(String test) throws NotFoundException {
//		Optional<Test> test = testRepository.findById(id);
//		if(test.isPresent()) {
//			return test.get();
//		}else {
//			throw new ResourceNotFoundException("Test", "Id", id);
//		}
        return testRepository.findById(test).orElseThrow(() ->
                new NotFoundException(String.format(ERR_EMAIL_NOT_FOUND, test)));

    }


    @Override
    public void deleteTest(String test) throws NotFoundException {

        // check whether a test exist in a DB or not
        testRepository.findById(test).orElseThrow(() ->
                new NotFoundException(String.format(ERR_EMAIL_NOT_FOUND, test)));
        testRepository.deleteById(test);
    }


}
