package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;
import com.tithe_system.tithe_management_system.utils.dtos.ProvinceDto;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProvinceResponse extends CommonResponse{
    private ProvinceDto provinceDto;
    List<ProvinceDto> provinceDtoList;
    Page<ProvinceDto> provinceDtoPage;

    public ProvinceDto getProvinceDto() {
        return provinceDto;
    }

    public void setProvinceDto(ProvinceDto provinceDto) {
        this.provinceDto = provinceDto;
    }

    public List<ProvinceDto> getProvinceDtoList() {
        return provinceDtoList;
    }

    public void setProvinceDtoList(List<ProvinceDto> provinceDtoList) {
        this.provinceDtoList = provinceDtoList;
    }

    public Page<ProvinceDto> getProvinceDtoPage() {
        return provinceDtoPage;
    }

    public void setProvinceDtoPage(Page<ProvinceDto> provinceDtoPage) {
        this.provinceDtoPage = provinceDtoPage;
    }

    @Override
    public String toString() {
        return "ProvinceResponse{" +
                "provinceDto=" + provinceDto +
                ", provinceDtoList=" + provinceDtoList +
                ", provinceDtoPage=" + provinceDtoPage +
                '}';
    }
}
