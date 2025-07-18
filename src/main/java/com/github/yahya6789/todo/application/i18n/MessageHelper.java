package com.github.yahya6789.todo.application.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.github.yahya6789.todo.domain.shared.EntityOperation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageHelper {
	public static final String API_SUCCESS_CODE = "api.success.";
	public static final String API_REL_CODE = "api.rel.";

	private final MessageSource messageSource;
	private Locale locale = LocaleContextHolder.getLocale();

	public String getApiSuccessMessage(EntityOperation operation) {
		return messageSource.getMessage(API_SUCCESS_CODE + operation.getName(), null, locale);
	}

	public String getApiRelMessage(String relKey) {
		return messageSource.getMessage(API_REL_CODE + relKey, null, locale);
	}
}