package com.jphaugla.repository;

import com.jphaugla.domain.Customer;
import com.jphaugla.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.jphaugla.util.Constants.ERR_CUSTOMER_NOT_FOUND_2;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

     Optional <List<Customer>> findByStateAbbreviationAndCity(String state, String city);

     Optional <List<Customer>> findByZipcodeAndLastName(String zipcode, String lastname);
}
