package com.jphaugla.service;

import com.jphaugla.domain.Email;

import java.util.List;
import java.util.UUID;

public interface EmailService {
    Email saveEmail (Email  email );
    List<Email > getAllEmails();
    Email  getEmailById(String id);
    void deleteEmail(String id);
    List<Email> getAllCustomerEmails(UUID CustomerId);

    void deleteCustomerEmails(UUID customerId);
}
