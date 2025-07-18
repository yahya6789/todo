package com.github.yahya6789.todo.application.api.hateoas;

/**
 * HATEOAS relation names for Todo API endpoints.
 */
public final class TodoRel {
	/** Relation name for all collection */
	public static final String TODOS = "todos";

	/** Relation name for update operation */
	public static final String UPDATE = "update";

	/** Relation name for delete operation */
	public static final String DELETE = "delete";

	/** Relation name for toggle completion operation */
	public static final String TOGGLE_COMPLETED = "toggle-completed";

	private TodoRel() {
	}
}
