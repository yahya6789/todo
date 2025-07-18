package com.github.yahya6789.todo.application.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
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
import com.github.yahya6789.todo.application.api.hateoas.TodoRel;
import com.github.yahya6789.todo.application.dto.request.CreateTodoDto;
import com.github.yahya6789.todo.application.dto.request.UpdateTodoDto;
import com.github.yahya6789.todo.application.dto.response.TodoDto;
import com.github.yahya6789.todo.application.i18n.MessageHelper;
import com.github.yahya6789.todo.domain.model.Todo;
import com.github.yahya6789.todo.domain.service.TodoService;
import com.github.yahya6789.todo.domain.shared.EntityOperation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
	private final TodoService service;
	private final MessageHelper messageHelper;

	@GetMapping
	public ApiResponse<PagedModel<EntityModel<TodoDto>>> findAll(Pageable pageable,
			PagedResourcesAssembler<Todo> assembler) {
		Page<Todo> page = service.findAll(pageable);
		PagedModel<EntityModel<TodoDto>> pagedModel = assembler.toModel(page, this::toEntityModel);

		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.FETCH), pagedModel);
	}

	@GetMapping("/{id}")
	public ApiResponse<EntityModel<TodoDto>> findById(@PathVariable Long id) {
		Todo todo = service.findById(id);
		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.FOUND),
				toEntityModel(todo));
	}

	@PostMapping
	public ApiResponse<EntityModel<TodoDto>> create(@Valid @RequestBody CreateTodoDto dto) {
		Todo todo = service.create(dto);
		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.CREATE),
				toEntityModel(todo));
	}

	@PutMapping("/{id}")
	public ApiResponse<EntityModel<TodoDto>> update(@PathVariable Long id, @Valid @RequestBody UpdateTodoDto dto) {
		Todo todo = service.update(id, dto);
		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.UPDATE),
				toEntityModel(todo));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.DELETE), null);
	}

	@PatchMapping("/{id}/toggle-completed")
	public ApiResponse<EntityModel<TodoDto>> toggleComplete(@PathVariable Long id) {
		Todo todo = service.toggleCompleted(id);
		return ApiResponseFactory.success(messageHelper.getApiSuccessMessage(EntityOperation.UPDATE),
				toEntityModel(todo));
	}

	private EntityModel<TodoDto> toEntityModel(Todo todo) {
		TodoDto dto = todo.toDto();

		EntityModel<TodoDto> model = EntityModel.of(dto,
				linkTo(methodOn(TodoController.class).findById(dto.getId())).withSelfRel(),
				linkTo(methodOn(TodoController.class).findAll(Pageable.unpaged(), null))
						.withRel(messageHelper.getApiRelMessage(TodoRel.TODOS)));

		model.add(linkTo(methodOn(TodoController.class).toggleComplete(dto.getId()))
				.withRel(messageHelper.getApiRelMessage(TodoRel.TOGGLE_COMPLETED)).withType(HttpMethod.PUT.name()));

		if (!dto.isCompleted()) {
			model.add(linkTo(methodOn(TodoController.class).update(dto.getId(), null))
					.withRel(messageHelper.getApiRelMessage(TodoRel.UPDATE)).withType(HttpMethod.PUT.name()));
			model.add(linkTo(methodOn(TodoController.class).delete(dto.getId()))
					.withRel(messageHelper.getApiRelMessage(TodoRel.DELETE)).withType(HttpMethod.DELETE.name()));
		}
		return model;
	}
}
