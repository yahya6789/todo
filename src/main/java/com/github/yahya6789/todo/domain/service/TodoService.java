package com.github.yahya6789.todo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.yahya6789.todo.application.dto.request.CreateTodoDto;
import com.github.yahya6789.todo.application.dto.request.UpdateTodoDto;
import com.github.yahya6789.todo.domain.model.Todo;
import com.github.yahya6789.todo.domain.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService implements DomainService {
	private final TodoRepository repository;

	public Page<Todo> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Todo findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
	}

	public Todo create(CreateTodoDto todoDto) {
		return repository.save(new Todo(todoDto.getTitle()));
	}

	public Todo update(Long id, UpdateTodoDto dto) {
		Todo existing = findById(id);
		existing.setTitle(dto.getTitle());
		existing.setCompleted(dto.isCompleted());
		return repository.save(existing);
	}

	public void delete(Long id) {
		if (!repository.existsById(id))
			throw new EntityNotFoundException(MSG_ENTITY_NOT_FOUND);
		repository.deleteById(id);
	}

	public Todo toggleCompleted(Long id) {
		Todo todo = findById(id);
		todo.setCompleted(!todo.isCompleted());
		return repository.save(todo);
	}
}
