package com.diego.finances.dto;

import java.time.LocalDateTime;

import com.diego.finances.model.Transaction;
import com.diego.finances.model.Type;

public class TransactionResponse {
	private Long id;
	private Type type;
	private double amount;
	private String description;
	private String category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public TransactionResponse(Transaction transaction) {
		this.id = transaction.getId();
		this.type = transaction.getType();
		this.amount = transaction.getAmount();
		this.description = transaction.getDescription();
		this.category = transaction.getCategory().getDescription();
		this.createdAt = transaction.getCreatedAt();
		this.updatedAt = transaction.getUpdatedAt();
	}
	
	public Long getId() {
		return id;
	}
	public Type getType() {
		return type;
	}
	public double getAmount() {
		return amount;
	}
	public String getDescription() {
		return description;
	}
	public String getCategory() {
		return category;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}