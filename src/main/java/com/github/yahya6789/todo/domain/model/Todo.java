package com.github.yahya6789.todo.domain.model;

import com.github.yahya6789.todo.application.dto.response.TodoDto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Todo extends AuditableEntity implements HasDto<TodoDto> {
	private String title;
	private boolean completed;

	public Todo(String title) {
		this.title = title;
	}

	// Required by JPA
	protected Todo() {
	}

	@Override
	public TodoDto toDto() {
		TodoDto dto = new TodoDto();
		dto.setId(getId());
		dto.setTitle(getTitle());
		dto.setCompleted(isCompleted());
		dto.setCreatedAt(getCreatedAt());
		dto.setUpdatedAt(getUpdatedAt());
		dto.setCreatedBy(getCreatedBy());
		dto.setUpdatedBy(getUpdatedBy());
		return dto;
	}
}
