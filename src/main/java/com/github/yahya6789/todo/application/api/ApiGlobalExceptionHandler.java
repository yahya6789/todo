package com.github.yahya6789.todo.application.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ApiGlobalExceptionHandler {
	// Validation errors (e.g. @NotBlank)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(ApiResponseFactory.failure("Validation failed", errors));
	}

	// Entity not found (e.g. findById throws EntityNotFoundException)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleEntityNotFound(EntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseFactory.failure(e.getMessage()));
	}

	// Constraint violations (e.g. unique email) in DTO
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse<String>> handleDataIntegrity(DataIntegrityViolationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseFactory.failure("Duplicate or invalid data"));
	}

	// RestClientException
	@ExceptionHandler(RestClientException.class)
	public ResponseEntity<ApiResponse<String>> handleRestClientException(RestClientException e) {
		return ResponseEntity.badRequest().body(ApiResponseFactory.failure(e.getMessage()));
	}

	// Optional: IllegalArgumentException for service-layer validation
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(ApiResponseFactory.failure(e.getMessage()));
	}

	// Catch-all fallback
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneric(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseFactory.failure(e.getMessage()));
	}
}
