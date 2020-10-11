package com.diego.finances.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Preenchimento obrigatório")
	@Enumerated(EnumType.STRING)
	private Type type;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	@Digits(integer = 7, fraction = 2, message="Apenas números com duas casas decimais")
	private double amount;
	@NotBlank(message = "Preenchimento obrigatório")
	private String description;
	@NotNull(message = "Preenchimento obrigatório")
	@ManyToOne
	private Category category;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt;
	
	@Deprecated
	public Transaction() {}
	
	public Transaction(@NotBlank(message = "Preenchimento obrigatório") String type, 
			@NotBlank(message = "Preenchimento obrigatório") LocalDate date, 
			@Digits(integer = 7, fraction = 2, message="Apenas números com duas casas decimais") double amount, 
			@NotBlank(message = "Preenchimento obrigatório") String description, 
			@NotNull(message = "Preenchimento obrigatório") Category category) {
		this.type = Type.valueOf(type);
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.category = category;
	}
	
	public void update(Transaction transaction) {
		this.type = transaction.type;
		this.date = transaction.date;
		this.amount = transaction.amount;
		this.description = transaction.description;
		this.category = transaction.category;
		this.updatedAt = LocalDateTime.now();
	}
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
}
