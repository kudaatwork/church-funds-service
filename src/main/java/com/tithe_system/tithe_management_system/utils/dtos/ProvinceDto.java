package com.tithe_system.tithe_management_system.utils.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceDto {
    private Long id;
    private String name;
    private List<RegionDto> regionDtoList;
    private List<DistrictDto> districtDtoList;
    private List<AssemblyDto> assemblyDtoList;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    private EntityStatus entityStatus;

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

    public List<RegionDto> getRegionDtoList() {
        return regionDtoList;
    }

    public void setRegionDtoList(List<RegionDto> regionDtoList) {
        this.regionDtoList = regionDtoList;
    }

    public List<DistrictDto> getDistrictDtoList() {
        return districtDtoList;
    }

    public void setDistrictDtoList(List<DistrictDto> districtDtoList) {
        this.districtDtoList = districtDtoList;
    }

    public List<AssemblyDto> getAssemblyDtoList() {
        return assemblyDtoList;
    }

    public void setAssemblyDtoList(List<AssemblyDto> assemblyDtoList) {
        this.assemblyDtoList = assemblyDtoList;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    @Override
    public String toString() {
        return "ProvinceDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regionDtoList=" + regionDtoList +
                ", districtDtoList=" + districtDtoList +
                ", assemblyDtoList=" + assemblyDtoList +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                '}';
    }
}
