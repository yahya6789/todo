package com.github.yahya6789.todo.application.api;

public class ApiResponseFactory {
	public static final String MSG_SUCCESS_FETCH = "Entities fetched";
	public static final String MSG_SUCCESS_FOUND = "Entity found";
	public static final String MSG_SUCCESS_CREATE = "Entity created";
	public static final String MSG_SUCCESS_UPDATE = "Entity updated";
	public static final String MSG_SUCCESS_DELETE = "Entity deleted";

	public static <T> ApiResponse<T> successFetch(T data) {
		return success(MSG_SUCCESS_FETCH, data);
	}

	public static <T> ApiResponse<T> successFound(T data) {
		return success(MSG_SUCCESS_FOUND, data);
	}

	public static <T> ApiResponse<T> successCreate(T data) {
		return success(MSG_SUCCESS_CREATE, data);
	}

	public static <T> ApiResponse<T> successUpdate(T data) {
		return success(MSG_SUCCESS_UPDATE, data);
	}

	public static <T> ApiResponse<T> successDelete(T data) {
		return success(MSG_SUCCESS_DELETE, data);
	}

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
