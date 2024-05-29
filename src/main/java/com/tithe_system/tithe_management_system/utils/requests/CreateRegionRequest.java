package com.tithe_system.tithe_management_system.utils.requests;

public class CreateRegionRequest {
    private String name;
    private Long provinceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "CreateRegionRequest{" +
                "name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
}
