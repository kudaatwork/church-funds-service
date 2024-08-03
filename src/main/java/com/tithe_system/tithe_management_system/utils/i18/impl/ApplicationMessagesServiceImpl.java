package com.tithe_system.tithe_management_system.utils.i18.impl;

import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import org.springframework.context.MessageSource;
import java.util.Locale;

public class ApplicationMessagesServiceImpl implements ApplicationMessagesService {
    private final MessageSource messageSource;

    public ApplicationMessagesServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getApplicationMessage(String propertyName, String[] placeholders, Locale locale) {
        return messageSource.getMessage(propertyName, placeholders, locale);
    }

    @Override
    public String getApplicationMessage(String propertyName, Locale locale) {
        return messageSource.getMessage(propertyName, new String[] {}, locale);
    }
}
