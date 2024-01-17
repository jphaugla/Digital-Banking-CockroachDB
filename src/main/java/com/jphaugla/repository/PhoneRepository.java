package com.jphaugla.repository;

import com.jphaugla.domain.Email;
import com.jphaugla.domain.Phone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface PhoneRepository extends JpaRepository<Phone, String> {
    List<Phone> findByCustomerId(UUID customerId);

}
