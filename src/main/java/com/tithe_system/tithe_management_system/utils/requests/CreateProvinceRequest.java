package com.tithe_system.tithe_management_system.utils.requests;

public class CreateProvinceRequest {
    private String name;
    private Long regionId;

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
        return "CreateProvinceRequest{" +
                "name='" + name + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
