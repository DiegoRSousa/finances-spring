package com.diego.finances.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.diego.finances.exception.DataIntegrityException;
import com.diego.finances.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	@Cacheable("findAllCategory")
	public ResponseEntity<List<CategoryResponse>> findAll() {
		var categories = categoryRepository.findAll().stream().map(CategoryResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/page")
	@Cacheable("findAllPageCategory")
	public ResponseEntity<Page<CategoryResponse>> findAllPage(
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pageRequest) {
		var categories = categoryRepository.findAll(pageRequest).map(CategoryResponse::new);
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponse> find(@PathVariable Long id) {
		var category = categoryService.findById(id);
		return ResponseEntity.ok(new CategoryResponse(category));
	}
	
	@GetMapping("/description/{description}")
	public ResponseEntity<List<CategoryResponse>> findByDescription(@PathVariable String description) {
		var categories = categoryRepository.findByDescriptionLike(description)
							.stream().map(CategoryResponse::new).collect(Collectors.toList());
		return ResponseEntity.ok(categories);
	}

	@PostMapping
	@CacheEvict(value = {"findAllCategory", "findAllPageCategory"}, allEntries = true)
	public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest) {
		var category = categoryRepository.save(categoryRequest.toModel());
		return new ResponseEntity<CategoryResponse>(new CategoryResponse(category), HttpStatus.CREATED);
	}	
	
	@PutMapping("/{id}")
	@CacheEvict(value = {"findAllCategory", "findAllPageCategory"}, allEntries = true)
	public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
		var category = categoryService.findById(id);
		category.update(categoryRequest.toModel());
		categoryRepository.save(category);
		return ResponseEntity.ok(new CategoryResponse(category));
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = {"findAllCategory", "findAllPageCategory"}, allEntries = true)
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