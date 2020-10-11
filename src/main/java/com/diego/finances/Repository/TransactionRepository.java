package com.diego.finances.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.finances.model.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

}
