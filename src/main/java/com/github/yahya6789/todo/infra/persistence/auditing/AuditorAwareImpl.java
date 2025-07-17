package com.github.yahya6789.todo.infra.persistence.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO: Replace with user from Spring Security if applicable
		return Optional.ofNullable(null);
	}
}
