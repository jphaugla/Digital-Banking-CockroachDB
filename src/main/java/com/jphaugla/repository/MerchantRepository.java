package com.jphaugla.repository;

import com.jphaugla.domain.Merchant;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface MerchantRepository extends JpaRepository<Merchant, String> {

}

