package com.github.yahya6789.todo.application.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.yahya6789.todo.application.api.ApiResponse;
import com.github.yahya6789.todo.application.api.ApiResponseFactory;
import com.github.yahya6789.todo.application.dto.request.CreateTodoDto;
import com.github.yahya6789.todo.application.dto.request.UpdateTodoDto;
import com.github.yahya6789.todo.application.dto.response.TodoDto;
import com.github.yahya6789.todo.domain.model.Todo;
import com.github.yahya6789.todo.domain.service.TodoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
	private final MessageSource messageSource;
	private final TodoService service;
	private Locale locale = LocaleContextHolder.getLocale();

	@GetMapping
	public ApiResponse<PagedModel<EntityModel<TodoDto>>> findAll(Pageable pageable,
			PagedResourcesAssembler<Todo> assembler) {
		Page<Todo> page = service.findAll(pageable);
		PagedModel<EntityModel<TodoDto>> pagedModel = assembler.toModel(page, this::toEntityModel);

		String message = messageSource.getMessage("api.success.fetch", null, locale);
		return ApiResponseFactory.success(message, pagedModel);
	}

	@GetMapping("/{id}")
	public ApiResponse<EntityModel<TodoDto>> findById(@PathVariable Long id) {
		Todo todo = service.findById(id);
		String message = messageSource.getMessage("api.success.found", null, locale);
		return ApiResponseFactory.success(message, toEntityModel(todo));
	}

	@PostMapping
	public ApiResponse<EntityModel<TodoDto>> create(@Valid @RequestBody CreateTodoDto dto) {
		Todo todo = service.create(dto);
		String message = messageSource.getMessage("api.success.create", null, locale);
		return ApiResponseFactory.success(message, toEntityModel(todo));
	}

	@PutMapping("/{id}")
	public ApiResponse<EntityModel<TodoDto>> update(@PathVariable Long id, @Valid @RequestBody UpdateTodoDto dto) {
		Todo todo = service.update(id, dto);
		String message = messageSource.getMessage("api.success.update", null, locale);
		return ApiResponseFactory.success(message, toEntityModel(todo));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		service.delete(id);
		String message = messageSource.getMessage("api.success.delete", null, locale);
		return ApiResponseFactory.success(message, null);
	}

	@PatchMapping("/{id}/toggle-completed")
	public ApiResponse<EntityModel<TodoDto>> toggleComplete(@PathVariable Long id) {
		Todo todo = service.toggleCompleted(id);
		String message = messageSource.getMessage("api.success.update", null, locale);
		return ApiResponseFactory.success(message, toEntityModel(todo));
	}

	private EntityModel<TodoDto> toEntityModel(Todo todo) {
		TodoDto dto = todo.toDto();

		EntityModel<TodoDto> model = EntityModel.of(dto,
				linkTo(methodOn(TodoController.class).findById(dto.getId())).withSelfRel(),
				linkTo(methodOn(TodoController.class).findAll(Pageable.unpaged(), null))
						.withRel(messageSource.getMessage("api.rel.todos", null, locale)));

		model.add(linkTo(methodOn(TodoController.class).toggleComplete(dto.getId()))
				.withRel(messageSource.getMessage("api.rel.toggle-completed", null, locale)).withType("PUT"));

		if (!dto.isCompleted()) {
			model.add(linkTo(methodOn(TodoController.class).update(dto.getId(), null))
					.withRel(messageSource.getMessage("api.rel.update", null, locale)).withType("PUT"));
			model.add(linkTo(methodOn(TodoController.class).delete(dto.getId()))
					.withRel(messageSource.getMessage("api.rel.delete", null, locale)).withType("DELETE"));
		}
		return model;
	}
}
