package com.tithe_system.tithe_management_system.utils.requests;

public class CreateUserRoleRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateUserRoleRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
