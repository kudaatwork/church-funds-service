package com.tithe_system.tithe_management_system.utils.i18.api;

import java.util.Locale;

public interface ApplicationMessagesService {
    String getMessage(String propertyName, String[] placeholders, Locale locale);
    String getMessage(String propertyName, Locale locale);
}
