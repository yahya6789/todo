package com.github.yahya6789.todo.application.api;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> extends RepresentationModel<ApiResponse<T>> {
	private final boolean success;
	private final String message;
	private final T data;
}
