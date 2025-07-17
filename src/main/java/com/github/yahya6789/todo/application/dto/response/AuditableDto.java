package com.github.yahya6789.todo.application.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class AuditableDto {
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String createdBy;

	private String updatedBy;
}
