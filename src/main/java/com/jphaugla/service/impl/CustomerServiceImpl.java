package com.jphaugla.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import com.jphaugla.domain.Account;
import com.jphaugla.domain.Customer;
import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;
import com.jphaugla.exception.NotFoundException;
import com.jphaugla.repository.AccountRepository;
import com.jphaugla.repository.CustomerRepository;

import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.CustomerService;
import com.jphaugla.service.DataGeneratorService;
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
    private AccountRepository accountRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private DataGeneratorService dataGeneratorService;
    @Value("${app.region}")
    private static String source_region;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("customerService.saveCustomer");
        customer.setCurrentTime(source_region);
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
        log.info("starting saveSampleCustoemr");
        Date create_date = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-28");
        Timestamp create_timestamp = new Timestamp((create_date.getTime()));
        Date last_update = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-29");
        Timestamp last_update_timestamp = new Timestamp((last_update.getTime()));

        Customer customer = new Customer("4744 17th av s", "",
                "Home", "N", "Minneapolis", "00",
                "jph", create_timestamp, "IDR",
                "A", "BANK", "1949.01.23",
                "Ralph", "Ralph Waldo Emerson", "M", "Emerson",
                last_update_timestamp, "jph", "Waldo", "MR",
                "help", "MN", "55444", "55444-3322"
        );
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
