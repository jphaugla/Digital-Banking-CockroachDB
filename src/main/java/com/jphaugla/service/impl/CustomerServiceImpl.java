package com.jphaugla.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.jphaugla.data.BankGenerator;
import com.jphaugla.domain.Account;
import com.jphaugla.domain.Customer;
import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;
import com.jphaugla.exception.ResourceNotFoundException;
import com.jphaugla.repository.AccountRepository;
import com.jphaugla.repository.CustomerRepository;

import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("customerService.saveCustomer");
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(UUID id) {
//		Optional<Customer> customer = customerRepository.findById(id);
//		if(customer.isPresent()) {
//			return customer.get();
//		}else {
//			throw new ResourceNotFoundException("Customer", "Id", id);
//		}
        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Id", id));

    }

    @Override
    public Customer updateCustomer(Customer customer, UUID id) {

        // we need to check whether customer with given id is exist in DB or not
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Id", id));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        //existingCustomer.setEmail(customer.getEmail());
        // save existing customer to DB
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }

    @Override
    public void deleteCustomer(UUID id) {

        // check whether a customer exist in a DB or not
        customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "Id", id));
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
                "Ralph", "Ralph Waldo Emerson", "M",
                "887778989", "SSN", "Emerson",
                last_update_timestamp, "jph", "Waldo", "MR",
                "help", "MN", "55444", "55444-3322"
        );
        log.info("Customer Saved");
        customerRepository.save(customer);
        UUID cust = customer.getId();
        Email home_email = new Email("jasonhaugland@gmail.com", "home", cust);
        Email work_email = new Email("jhaugland@cockroachlabs.com", "work", cust);
        Phone cell_phone = new Phone("612-408-4394", "cell", cust);
        emailRepository.save(home_email);
        emailRepository.save(work_email);
        log.info("Email saved");
        phoneRepository.save(cell_phone);
        log.info("phone saved ");
        return customer;
    }

    @Override
    public List<Customer> getCustomerByStateCity(String state, String city) {
        return customerRepository.findByStateAbbreviationAndCity(state, city);
    }

    @Override
    public List<Customer> getCustomerByZipLast(String zipcode, String lastname) {
        return customerRepository.findByZipcodeAndLastName(zipcode, lastname);
    }

    @Override
    public List<Account> createCustomerAccount(int noOfCustomers, String key_suffix) throws ExecutionException {

        log.info("Creating " + noOfCustomers + " customers with accounts and suffix " + key_suffix);
        BankGenerator.Timer custTimer = new BankGenerator.Timer();
        List<Account> accounts = null;
        List<Account> allAccounts = new ArrayList<>();
        List<Email> emails = null;
        List<Phone> phoneNumbers = null;
        int totalAccounts = 0;
        int totalEmails = 0;
        int totalPhone = 0;
        log.info("before the big for loop");
        for (int i = 0; i < noOfCustomers; i++) {
            // log.info("int noOfCustomer for loop i=" + i);
            Customer customer = BankGenerator.createRandomCustomer(key_suffix);
            List<Email> emailList = BankGenerator.createEmail(customer.getId());
            List<Phone> phoneList = BankGenerator.createPhone(customer.getId());
            for (Phone phoneNumber : phoneNumbers = phoneList) {
                phoneRepository.save(phoneNumber);
            }
            totalPhone = totalPhone + phoneNumbers.size();
            for (Email email : emails = emailList) {
                emailRepository.save(email);
            }
            totalEmails = totalEmails + emails.size();
            accounts = BankGenerator.createRandomAccountsForCustomer(customer, key_suffix);
            totalAccounts = totalAccounts + accounts.size();
            for (Account account : accounts) {
                accountRepository.save(account);
            }
            customerRepository.save(customer);
            if (!accounts.isEmpty()) {
                allAccounts.addAll(accounts);
            }
        }

        custTimer.end();
        log.info("Customers=" + noOfCustomers + " Accounts=" + totalAccounts +
                " Emails=" + totalEmails + " Phones=" + totalPhone + " in " +
                custTimer.getTimeTakenSeconds() + " secs");
        return allAccounts;
    }

}
