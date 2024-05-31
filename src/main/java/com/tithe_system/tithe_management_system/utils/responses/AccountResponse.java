package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse extends CommonResponse {
    private AccountDto accountDto;
    private List<AccountDto> accountDtoList;
    private Page<AccountDto> accountDtoPage;

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    public List<AccountDto> getAccountDtoList() {
        return accountDtoList;
    }

    public void setAccountDtoList(List<AccountDto> accountDtoList) {
        this.accountDtoList = accountDtoList;
    }

    public Page<AccountDto> getAccountDtoPage() {
        return accountDtoPage;
    }

    public void setAccountDtoPage(Page<AccountDto> accountDtoPage) {
        this.accountDtoPage = accountDtoPage;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "accountDto=" + accountDto +
                ", accountDtoList=" + accountDtoList +
                ", accountDtoPage=" + accountDtoPage +
                '}';
    }
}
