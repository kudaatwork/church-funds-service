package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.RegionDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionResponse extends CommonResponse{
    private RegionDto regionDto;
    private List<RegionDto> regionDtoList;
    private Page<RegionDto> regionDtoPage;

    public RegionDto getRegionDto() {
        return regionDto;
    }

    public void setRegionDto(RegionDto regionDto) {
        this.regionDto = regionDto;
    }

    public List<RegionDto> getRegionDtoList() {
        return regionDtoList;
    }

    public void setRegionDtoList(List<RegionDto> regionDtoList) {
        this.regionDtoList = regionDtoList;
    }

    public Page<RegionDto> getRegionDtoPage() {
        return regionDtoPage;
    }

    public void setRegionDtoPage(Page<RegionDto> regionDtoPage) {
        this.regionDtoPage = regionDtoPage;
    }

    @Override
    public String toString() {
        return "RegionResponse{" +
                "regionDto=" + regionDto +
                ", regionDtoList=" + regionDtoList +
                ", regionDtoPage=" + regionDtoPage +
                '}';
    }
}
