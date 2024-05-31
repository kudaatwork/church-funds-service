package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import java.util.Optional;

public interface UserAccountRepository {
    Optional<UserAccount> findByIdAndEntityStatusNot(Long userAccountId, EntityStatus entityStatus);
}
