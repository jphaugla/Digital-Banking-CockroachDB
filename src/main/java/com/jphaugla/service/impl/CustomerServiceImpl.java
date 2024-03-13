package com.jphaugla.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;

import java.util.List;
import java.util.UUID;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphaugla.domain.Customer;
import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.CustomerRepository;

import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.jphaugla.util.Constants.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Value("${app.region}")
    private String source_region;
    @Autowired
    ObjectMapper objectMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws JsonProcessingException {
        log.info("customerService.saveCustomer with source_region: " + source_region);
        customer.setCurrentTime(source_region);
        customer.set_source(source_region);
        String jsonStr = objectMapper.writeValueAsString(customer);
        log.info("customer is " + jsonStr);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(UUID id) throws NotFoundException {
//		Optional<Customer> customer = customerRepository.findById(id);
//		if(customer.isPresent()) {
//			return customer.get();
//		}else {
//			throw new ResourceNotFoundException("Customer", "Id", id);
//		}
        return customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ERR_CUSTOMER_NOT_FOUND, id)));

    }

    @Override
    public Customer updateCustomer(Customer customer, UUID id) throws NotFoundException {

        // we need to check whether customer with given id is exist in DB or not
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ERR_CUSTOMER_NOT_FOUND, id)));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        //existingCustomer.setEmail(customer.getEmail());
        // save existing customer to DB
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }

    @Override
    public void deleteCustomer(UUID id) throws NotFoundException {

        // check whether a customer exist in a DB or not
        customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(ERR_CUSTOMER_NOT_FOUND, id)));
        customerRepository.deleteById(id);
    }
    @Override
    public Customer saveSampleCustomer() throws ParseException {
        log.info("starting saveSampleCustomer");
        long currentMillis = System.currentTimeMillis();
        Timestamp timestamp_current = new Timestamp(currentMillis);

        Customer customer = new Customer("4744 17th av s", "",
                "Home", "N", "Minneapolis", "00",
                "jph", timestamp_current, "IDR",
                "A", "BANK", "1949.01.23",
                "Ralph", "Ralph Waldo Emerson", "M", "Emerson",
                timestamp_current, "jph", "Waldo", "MR",
                "help", "MN", "55444", "55444-3322"
        );
        customer.set_source(source_region);
        customer.set_timestamp(timestamp_current);
        log.info("Customer Saved");
        customerRepository.save(customer);
        UUID cust = customer.getId();
        Email home_email = new Email("jasonhaugland@gmail.com", "home", cust, source_region);
        Email work_email = new Email("jhaugland@cockroachlabs.com", "work", cust,source_region);
        Phone cell_phone = new Phone("612-408-4394", "cell", cust, source_region);
        emailRepository.save(home_email);
        emailRepository.save(work_email);
        log.info("Email saved");
        phoneRepository.save(cell_phone);
        log.info("phone saved ");
        return customer;
    }

    @Override
    public List<Customer> getCustomerByStateCity(String state, String city) throws NotFoundException {
        return customerRepository.findByStateAbbreviationAndCity(state, city).orElseThrow(() ->
                new NotFoundException(String.format(ERR_CUSTOMER_NOT_FOUND_2, "State:" + state + " City: " + city)));
    }

    @Override
    public List<Customer> getCustomerByZipLast(String zipcode, String lastname) throws NotFoundException {
        return customerRepository.findByZipcodeAndLastName(zipcode, lastname).orElseThrow(() ->
                new NotFoundException(String.format(ERR_CUSTOMER_NOT_FOUND_2, "Zip:" + zipcode + " City: " + lastname)));
    }


}
