package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictResponse extends CommonResponse {
    private DistrictDto districtDto;
    private List<DistrictDto> districtDtoList;
    private Page<DistrictDto> districtDtoPage;

    public DistrictDto getDistrictDto() {
        return districtDto;
    }

    public void setDistrictDto(DistrictDto districtDto) {
        this.districtDto = districtDto;
    }

    public List<DistrictDto> getDistrictDtoList() {
        return districtDtoList;
    }

    public void setDistrictDtoList(List<DistrictDto> districtDtoList) {
        this.districtDtoList = districtDtoList;
    }

    public Page<DistrictDto> getDistrictDtoPage() {
        return districtDtoPage;
    }

    public void setDistrictDtoPage(Page<DistrictDto> districtDtoPage) {
        this.districtDtoPage = districtDtoPage;
    }

    @Override
    public String toString() {
        return "DistrictResponse{" +
                "districtDto=" + districtDto +
                ", districtDtoList=" + districtDtoList +
                ", districtDtoPage=" + districtDtoPage +
                '}';
    }
}
