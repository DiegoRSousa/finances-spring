package com.diego.finances.dto;

import java.time.LocalDateTime;

import com.diego.finances.model.Category;

public class CategoryResponse {

	private final Long id;
	private final String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public CategoryResponse(final Category category) {
		this.id = category.getId();
		this.description = category.getDescription();
		this.createdAt = category.getCreatedAt();
		this.updatedAt = category.getUpdatedAt();
	}
	
	public Long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}