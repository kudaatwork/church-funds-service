package com.tithe_system.tithe_management_system.utils.requests;

public class EditUserRoleRequest {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EditUserRoleRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
