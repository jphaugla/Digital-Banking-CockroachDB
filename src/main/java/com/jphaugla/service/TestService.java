package com.jphaugla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Test;
import com.jphaugla.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface TestService {
    Test saveTest (Test  test );

    Test saveTestKafka(Test test) throws JsonProcessingException;

    List<Test > getAllTests();
    Test  getTestById(String id) throws NotFoundException;
    void deleteTest(String id) throws NotFoundException;


}
