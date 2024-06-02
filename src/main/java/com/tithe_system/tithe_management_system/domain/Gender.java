package com.tithe_system.tithe_management_system.domain;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");
    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
