package com.diego.finances.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diego.finances.Repository.TransactionRepository;
import com.diego.finances.dto.TransactionRequest;
import com.diego.finances.dto.TransactionResponse;
import com.diego.finances.exception.ObjectNotFoundException;
import com.diego.finances.model.Transaction;
import com.diego.finances.service.CategoryService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping 
	public ResponseEntity<List<TransactionResponse>> findAll() {
		var transactions = transactionRepository.findAll().stream().map(TransactionResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<TransactionResponse>> findAllPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
		var pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		var transactions = transactionRepository.findAll(pageRequest).map(TransactionResponse::new);
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransactionResponse> find(@PathVariable Long id) {
		var transaction = findById(id);
		return ResponseEntity.ok(new TransactionResponse(transaction));
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<TransactionResponse>> findByDescription(@PathVariable String description) {
		var transactions = transactionRepository.findByDescriptionLike(description)
								.stream().map(TransactionResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(transactions);
	}
	
	@PostMapping
	public ResponseEntity<TransactionResponse> save(@Valid @RequestBody TransactionRequest transactionRequest) {
		var category = categoryService.findById(transactionRequest.getCategoryId());	
		var transaction = transactionRepository.save(transactionRequest.toModel(category));
		return new ResponseEntity<TransactionResponse>(new TransactionResponse(transaction), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Transaction> update(@PathVariable Long id, 
				@Valid @RequestBody TransactionRequest transactionRequest) {
		var transaction = findById(id);
		var category = categoryService.findById(transactionRequest.getCategoryId());
		transaction.update(transactionRequest.toModel(category));
		transactionRepository.save(transaction);
		return ResponseEntity.ok(transaction);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		var transaction = findById(id);
		transactionRepository.delete(transaction);
		return ResponseEntity.noContent().build();
	}
	
	private Transaction findById(Long id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(id, Transaction.class.getSimpleName()));
	}
}