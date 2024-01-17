package com.jphaugla.repository;


import com.jphaugla.domain.Email;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

    List<Email> findByCustomerId(UUID customerId);
}
