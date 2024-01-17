package com.jphaugla.repository;


import com.jphaugla.domain.TransactionReturn;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface TransactionReturnRepository  extends JpaRepository<TransactionReturn, String> {

}
