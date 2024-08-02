package com.tithe_system.tithe_management_system.domain;

public enum Title {
    PASTOR("PASTOR"), SECRETARY("SECRETARY"), TREASURER("TREASURER"), ADMINISTRATOR("ADMINISTRATOR");
    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
