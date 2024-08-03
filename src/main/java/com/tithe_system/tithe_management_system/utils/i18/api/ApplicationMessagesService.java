package com.tithe_system.tithe_management_system.utils.i18.api;

import java.util.Locale;

public interface ApplicationMessagesService {
    String getApplicationMessage(String propertyName, String[] placeholders, Locale locale);
    String getApplicationMessage(String propertyName, Locale locale);
}
