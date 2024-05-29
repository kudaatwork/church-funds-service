package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssemblyResponse extends CommonResponse{
    private AssemblyDto assemblyDto;
    private List<AssemblyDto> assemblyDtoList;
    private Page<AssemblyDto> assemblyDtoPage;

    public AssemblyDto getAssemblyDto() {
        return assemblyDto;
    }

    public void setAssemblyDto(AssemblyDto assemblyDto) {
        this.assemblyDto = assemblyDto;
    }

    public List<AssemblyDto> getAssemblyDtoList() {
        return assemblyDtoList;
    }

    public void setAssemblyDtoList(List<AssemblyDto> assemblyDtoList) {
        this.assemblyDtoList = assemblyDtoList;
    }

    public Page<AssemblyDto> getAssemblyDtoPage() {
        return assemblyDtoPage;
    }

    public void setAssemblyDtoPage(Page<AssemblyDto> assemblyDtoPage) {
        this.assemblyDtoPage = assemblyDtoPage;
    }

    @Override
    public String toString() {
        return "AssemblyResponse{" +
                "assemblyDto=" + assemblyDto +
                ", assemblyDtoList=" + assemblyDtoList +
                ", assemblyDtoPage=" + assemblyDtoPage +
                '}';
    }
}
