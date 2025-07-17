package com.github.yahya6789.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.yahya6789.todo.domain.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
