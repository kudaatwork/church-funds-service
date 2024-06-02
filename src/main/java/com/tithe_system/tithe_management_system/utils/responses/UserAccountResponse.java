package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.UserAccountDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountResponse extends CommonResponse {
    private UserAccountDto userAccountDto;
    private List<UserAccountDto> userAccountDtoList;
    private Page<UserAccountDto> userAccountDtoPage;

    public UserAccountDto getUserAccountDto() {
        return userAccountDto;
    }

    public void setUserAccountDto(UserAccountDto userAccountDto) {
        this.userAccountDto = userAccountDto;
    }

    public List<UserAccountDto> getUserAccountDtoList() {
        return userAccountDtoList;
    }

    public void setUserAccountDtoList(List<UserAccountDto> userAccountDtoList) {
        this.userAccountDtoList = userAccountDtoList;
    }

    public Page<UserAccountDto> getUserAccountDtoPage() {
        return userAccountDtoPage;
    }

    public void setUserAccountDtoPage(Page<UserAccountDto> userAccountDtoPage) {
        this.userAccountDtoPage = userAccountDtoPage;
    }

    @Override
    public String toString() {
        return "UserAccountResponse{" +
                "userAccountDto=" + userAccountDto +
                ", userAccountDtoList=" + userAccountDtoList +
                ", userAccountDtoPage=" + userAccountDtoPage +
                '}';
    }
}
