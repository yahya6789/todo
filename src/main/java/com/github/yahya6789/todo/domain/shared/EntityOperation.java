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
	DELETE("delete");

	@Getter
	private final String name;

	EntityOperation(String name) {
		this.name = name;
	}
}
