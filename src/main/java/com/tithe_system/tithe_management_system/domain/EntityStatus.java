package com.tithe_system.tithe_management_system.domain;

public enum EntityStatus {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE"),DELETED("DELETED");

    private String status;

    EntityStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
