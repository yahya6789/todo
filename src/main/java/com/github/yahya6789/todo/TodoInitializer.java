package com.github.yahya6789.todo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.yahya6789.todo.domain.model.Todo;
import com.github.yahya6789.todo.domain.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TodoInitializer implements CommandLineRunner {

	private final TodoRepository repository;

	@Override
	public void run(String... args) throws Exception {
		if (repository.count() == 0) {
			for (int i = 0; i < 55; i++) {
				repository.save(new Todo("Todo " + i));
			}
		}
	}
}
