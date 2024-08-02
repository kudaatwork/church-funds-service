package com.tithe_system.tithe_management_system.utils.requests;

public class EditProvinceRequest {
    private Long id;
    private String name;
    private Long regionId;

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

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "EditProvinceRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
