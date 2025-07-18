package com.github.yahya6789.todo.application.api;

import org.springframework.stereotype.Component;

@Component
public class ApiResponseFactory {
	public static <T> ApiResponse<T> success(String msg, T data) {
		return new ApiResponse<>(true, msg, data);
	}

	public static <T> ApiResponse<T> failure(String msg, T data) {
		return new ApiResponse<>(false, msg, data);
	}

	public static <T> ApiResponse<T> failure(String msg) {
		return new ApiResponse<>(false, msg, null);
	}
}
