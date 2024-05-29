package com.tithe_system.tithe_management_system.utils.requests;

public class EditDistrictRequest {
    private Long id;
    private String name;
    private Long regionId;
    private Long provinceId;

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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "CreateDistrictRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionId=" + regionId +
                ", provinceId=" + provinceId +
                '}';
    }
}
