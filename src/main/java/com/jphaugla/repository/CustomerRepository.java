package com.jphaugla.repository;

import com.jphaugla.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

     List<Customer> findByStateAbbreviationAndCity(String state, String city);

     List<Customer> findByZipcodeAndLastName(String zipcode, String lastname);
}
