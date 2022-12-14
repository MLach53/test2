package com.spr.systemplacereservation.translator;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class TranslatorService {

	private ResourceBundleMessageSource messageSource;

	public TranslatorService(@Qualifier("textsResourceBundleMessageSource") ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String toLocale(String code, String... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, args, locale);
	}
}