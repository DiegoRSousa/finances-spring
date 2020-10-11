package com.diego.finances.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.diego.finances.CategoryService;
import com.diego.finances.Repository.CategoryRepository;
import com.diego.finances.dto.CategoryRequest;
import com.diego.finances.dto.CategoryResponse;
import com.diego.finances.exception.DataIntegrityException;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> findAll() {
		var categories = categoryRepository.findAll().stream().map(CategoryResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoryResponse>> findAllPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
		var pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		var categories = categoryRepository.findAll(pageRequest).map(CategoryResponse::new);
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> find(@PathVariable Long id) {
		var category = categoryService.findById(id);
		return ResponseEntity.ok(new CategoryResponse(category));
	}
	
	@PostMapping
	public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest) {
		var category = categoryRepository.save(categoryRequest.toModel());
		return new ResponseEntity<CategoryResponse>(new CategoryResponse(category), HttpStatus.CREATED);
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
		var category = categoryService.findById(id);
		category.update(categoryRequest.toModel());
		categoryRepository.save(category);
		return ResponseEntity.ok(new CategoryResponse(category));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		var category = categoryService.findById(id);
		try {
			categoryRepository.delete(category);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é posível excluir uma categoria que possui produtos!");
		}

		return ResponseEntity.noContent().build();
	}	
}