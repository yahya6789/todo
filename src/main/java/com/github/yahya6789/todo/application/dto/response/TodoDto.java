package com.github.yahya6789.todo.application.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TodoDto extends AuditableDto implements ResponseDto {
	private long id;

	private String title;

	private boolean completed;
}
