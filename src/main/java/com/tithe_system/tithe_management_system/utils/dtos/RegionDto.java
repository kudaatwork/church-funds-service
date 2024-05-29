package com.tithe_system.tithe_management_system.utils.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {
    private Long id;
    private String name;
    private List<DistrictDto> districtDtoList;
    private List<AssemblyDto> assemblyDtoList;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    private EntityStatus entityStatus;
    private ProvinceDto provinceDto;

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

    public ProvinceDto getProvinceDto() {
        return provinceDto;
    }

    public void setProvinceDto(ProvinceDto provinceDto) {
        this.provinceDto = provinceDto;
    }

    @Override
    public String toString() {
        return "RegionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", districtDtoList=" + districtDtoList +
                ", assemblyDtoList=" + assemblyDtoList +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                ", provinceDto=" + provinceDto +
                '}';
    }
}
