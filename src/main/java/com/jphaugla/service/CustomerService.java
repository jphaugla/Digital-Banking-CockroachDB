package com.jphaugla.service;

import com.jphaugla.domain.Account;
import com.jphaugla.domain.Customer;
import com.jphaugla.exception.NotFoundException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id) throws NotFoundException;
    Customer updateCustomer(Customer customer, UUID id) throws NotFoundException;
    void deleteCustomer(UUID id) throws NotFoundException;
    Customer saveSampleCustomer() throws ParseException;

    List<Customer> getCustomerByStateCity(String state, String city) throws NotFoundException;

    List<Customer> getCustomerByZipLast(String zipcode, String lastname) throws NotFoundException;
}
