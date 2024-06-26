package com.tithe_system.tithe_management_system.utils.i18.impl;

import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import org.springframework.context.MessageSource;
import java.util.Locale;

public class MessageServiceImpl implements MessageService {
    private final MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String propertyName, String[] placeholders, Locale locale) {
        return messageSource.getMessage(propertyName, placeholders, locale);
    }

    @Override
    public String getMessage(String propertyName, Locale locale) {
        return messageSource.getMessage(propertyName, new String[] {}, locale);
    }
}
