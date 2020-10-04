package com.diego.finances.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.finances.Repository.CategoryRepository;
import com.diego.finances.dto.CategoryRequest;
import com.diego.finances.dto.CategoryResponse;
import com.diego.finances.exception.ObjectNotFoundException;
import com.diego.finances.model.Category;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> findAll() {
		var categories = categoryRepository.findAll().stream().map(CategoryResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> find(@PathVariable Long id) {
		var category = findById(id);
		return ResponseEntity.ok(new CategoryResponse(category));
	}
	
	@PostMapping
	public ResponseEntity<Category> save(@Valid @RequestBody CategoryRequest categoryRequest) {
		var category = categoryRepository.save(categoryRequest.toModel());
		return new ResponseEntity<Category>(category, HttpStatus.CREATED);
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
		var category = findById(id);
		category.update(categoryRequest.toModel());
		categoryRepository.save(category);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		var category = findById(id);
		categoryRepository.delete(category);
		return ResponseEntity.noContent().build();
	}
	
	private Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
	}
}