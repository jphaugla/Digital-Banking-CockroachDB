package com.jphaugla.service.impl;

import com.jphaugla.domain.Email;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.EmailRepository;
import com.jphaugla.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.jphaugla.util.Constants.*;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
 
    @Autowired
    private EmailRepository emailRepository;


    public EmailServiceImpl(EmailRepository emailRepository) {
        super();
        this.emailRepository = emailRepository;
    }

    @Override
    public Email saveEmail(Email email) {
        log.info("emailService.saveEmail");
        return emailRepository.save(email);
    }

    @Override
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @Override
    public Email getEmailById(String email) throws NotFoundException {
//		Optional<Email> email = emailRepository.findById(id);
//		if(email.isPresent()) {
//			return email.get();
//		}else {
//			throw new ResourceNotFoundException("Email", "Id", id);
//		}
        return emailRepository.findById(email).orElseThrow(() ->
                new NotFoundException(String.format(ERR_EMAIL_NOT_FOUND, email)));

    }


    @Override
    public void deleteEmail(String email) throws NotFoundException {

        // check whether a email exist in a DB or not
        emailRepository.findById(email).orElseThrow(() ->
                new NotFoundException(String.format(ERR_EMAIL_NOT_FOUND, email)));
        emailRepository.deleteById(email);
    }
    @Override
    public List<Email> getAllCustomerEmails(UUID customerId) throws NotFoundException {
        return emailRepository.findByCustomerId(customerId).orElseThrow(() ->
                new NotFoundException(String.format(ERR_CUSTOMER_EMAIL_NOT_FOUND, customerId)));
    }

    @Override
    public void deleteCustomerEmails(UUID customerId) throws NotFoundException {
        List <Email> customerEmails = getAllCustomerEmails(customerId);
        for (Email email : customerEmails) {
            deleteEmail(email.getAddress());
        }
    }

}
