package com.github.yahya6789.todo.domain.shared;

import lombok.Getter;

/**
 * Standard operations for entity life-cycle. Used for i18n message keys and
 * HATEOAS relations.
 */
public enum EntityOperation {
	FETCH("fetch"),
	FOUND("found"),
	CREATE("create"),
	UPDATE("update"),
	DELETE("delete"),
	TOGGLE_COMPLETE("toggle-complete");

	@Getter
	private final String key;

	EntityOperation(String key) {
		this.key = key;
	}
}
