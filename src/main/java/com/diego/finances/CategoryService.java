package com.diego.finances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.finances.Repository.CategoryRepository;
import com.diego.finances.exception.ObjectNotFoundException;
import com.diego.finances.model.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
	}
}
