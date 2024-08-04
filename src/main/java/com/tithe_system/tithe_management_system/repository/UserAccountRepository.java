package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {
    Optional<UserAccount> findByIdAndEntityStatusNot(Long userAccountId, EntityStatus entityStatus);
    Optional<UserAccount> findByPhoneNumberAndEntityStatusNot(String phoneNumber, EntityStatus entityStatus);
    List<UserAccount> findByEntityStatusNot(EntityStatus entityStatus);
    Page<UserAccount> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    Optional<UserAccount> findByEmailAddressAndEntityStatusNot(String emailAddress, EntityStatus entityStatus);
    Optional<UserAccount> findByUsernameAndEntityStatusNot(String username, EntityStatus entityStatus);
    Optional<UserAccount> findByPhoneNumberAndEmailAddressAndEntityStatusNot(String phoneNumber, String emailAddress, EntityStatus entityStatus);
    Page<UserAccount> findByUserGroupIdAndEntityStatusNot(Long id, EntityStatus entityStatus, Pageable pageable);
    Page<UserAccount> findByAssemblyIdAndEntityStatusNot(Long id, EntityStatus entityStatus, Pageable pageable);
}
