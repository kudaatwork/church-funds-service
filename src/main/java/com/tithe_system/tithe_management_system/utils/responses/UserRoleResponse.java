package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.UserRoleDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleResponse {
    private UserRoleDto userRoleDto;
    private List<UserRoleDto> userRoleDtoList;
    private Page<UserRoleDto> userRoleDtoPage;

    public UserRoleDto getUserRoleDto() {
        return userRoleDto;
    }

    public void setUserRoleDto(UserRoleDto userRoleDto) {
        this.userRoleDto = userRoleDto;
    }

    public List<UserRoleDto> getUserRoleDtoList() {
        return userRoleDtoList;
    }

    public void setUserRoleDtoList(List<UserRoleDto> userRoleDtoList) {
        this.userRoleDtoList = userRoleDtoList;
    }

    public Page<UserRoleDto> getUserRoleDtoPage() {
        return userRoleDtoPage;
    }

    public void setUserRoleDtoPage(Page<UserRoleDto> userRoleDtoPage) {
        this.userRoleDtoPage = userRoleDtoPage;
    }

    @Override
    public String toString() {
        return "UserRoleResponse{" +
                "userRoleDto=" + userRoleDto +
                ", userRoleDtoList=" + userRoleDtoList +
                ", userRoleDtoPage=" + userRoleDtoPage +
                '}';
    }
}
