package com.github.yahya6789.todo.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTodoDto implements RequestDto {
	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title cannot more than 100 characters")
	private String title;

	@NotNull
	private boolean completed;
}
