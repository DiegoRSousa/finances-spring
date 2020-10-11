package com.diego.finances.dto;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.diego.finances.model.Category;
import com.diego.finances.model.Transaction;

public class TransactionRequest {

	@NotBlank(message = "Preenchimento obrigatório")
	private String type;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	@Digits(integer = 7, fraction = 2, message="Apenas números com duas casas decimais")
	private double amount;
	@NotBlank(message = "Preenchimento obrigatório")
	private String description;
	@NotNull(message = "Preenchimento obrigatório")
	private Long categoryId;
	
	public Transaction toModel() {
		return new Transaction(type, date, amount, description, new Category(categoryId));
	}
	
	public String getType() {
		return type;
	}
	public LocalDate getDate() {
		return date;
	}
	public double getAmount() {
		return amount;
	}
	public String getDescription() {
		return description;
	}
	public Long getCategoryId() {
		return categoryId;
	}
}
