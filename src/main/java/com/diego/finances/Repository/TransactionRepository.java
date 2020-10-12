package com.diego.finances.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.finances.model.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

	List<Transaction> findByDescriptionLike(String description);
}
