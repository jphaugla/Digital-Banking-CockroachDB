package com.jphaugla.service;

import com.jphaugla.domain.Account;
import com.jphaugla.domain.Customer;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id);
    Customer updateCustomer(Customer customer, UUID id);
    void deleteCustomer(UUID id);
    Customer saveSampleCustomer() throws ParseException;

    List<Customer> getCustomerByStateCity(String state, String city);

    List<Customer> getCustomerByZipLast(String zipcode, String lastname);

    List<Account> createCustomerAccount(int noOfCustomers, String key_suffix) throws ExecutionException;
}
