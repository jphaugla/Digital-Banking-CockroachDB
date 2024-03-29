package com.jphaugla.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jphaugla.domain.Email;
import com.jphaugla.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface EmailService {
    Email saveEmail (Email  email );

    Email saveEmailKafka(Email email) throws JsonProcessingException;

    List<Email > getAllEmails();
    Email  getEmailById(String id) throws NotFoundException;
    void deleteEmail(String id) throws NotFoundException;
    List<Email> getAllCustomerEmails(UUID CustomerId) throws NotFoundException;

    void deleteCustomerEmails(UUID customerId) throws NotFoundException;
}
