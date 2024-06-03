package com.tithe_system.tithe_management_system.utils.generators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountAndReferencesGenerator {

    public static String getAccountNumber() {
        LocalDateTime date = LocalDateTime.now();
        String s = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS"));
        return s.replace(".", "");
    }

    public static Long getTransactionReference() {

        Long l = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            builder.append((int) (Math.random() * 10));
        }
        int randomNumber = (int) (Math.random() * 1000);

        String entityId = l + builder.toString() + randomNumber;

        try {
            l = Long.parseLong(entityId);
        } catch (Exception e) {
            return l;
        }
        return l;
    }
}
