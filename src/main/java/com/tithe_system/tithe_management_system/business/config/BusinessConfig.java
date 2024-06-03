package com.tithe_system.tithe_management_system.business.config;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.PaymentServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.ProvinceServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.RegionServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.UserAccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.UserGroupServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.UserRoleServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.impl.AccountServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.AssemblyServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.DistrictServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.PaymentServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.ProvinceServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.RegionServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.UserAccountServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.UserGroupServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.UserRoleServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.business.logic.api.DistrictService;
import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.business.logic.api.ProvinceService;
import com.tithe_system.tithe_management_system.business.logic.api.RegionService;
import com.tithe_system.tithe_management_system.business.logic.api.UserAccountService;
import com.tithe_system.tithe_management_system.business.logic.api.UserGroupService;
import com.tithe_system.tithe_management_system.business.logic.api.UserRoleService;
import com.tithe_system.tithe_management_system.business.logic.impl.AccountServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.AssemblyServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.DistrictServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.PaymentServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.ProvinceServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.RegionServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.UserAccountServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.UserGroupServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.UserRoleServiceImpl;
import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.AssemblyServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.DistrictServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.PaymentServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.ProvinceServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.RegionServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.UserAccountServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.UserGroupServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.UserRoleServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.impl.AccountServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.AssemblyServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.DistrictServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.PaymentServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.ProvinceServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.RegionServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.UserAccountServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.UserGroupServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.UserRoleServiceValidatorImpl;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.repository.PaymentRepository;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.repository.UserGroupRepository;
import com.tithe_system.tithe_management_system.repository.UserRoleRepository;
import com.tithe_system.tithe_management_system.repository.config.DataConfig;
import com.tithe_system.tithe_management_system.utils.config.UtilsConfig;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataConfig.class, UtilsConfig.class})
public class BusinessConfig {

    @Bean
    public ProvinceServiceAuditable provinceServiceAuditable(ProvinceRepository provinceRepository){
        return new ProvinceServiceAuditableImpl(provinceRepository);
    }

    @Bean
    public ProvinceServiceValidator provinceServiceValidator(){
        return new ProvinceServiceValidatorImpl();
    }

    @Bean
    public ProvinceService provinceService(ProvinceServiceValidator provinceServiceValidator, ProvinceRepository
                                           provinceRepository, RegionRepository regionRepository,
                                           DistrictRepository districtRepository, AssemblyRepository assemblyRepository,
                                           ModelMapper modelMapper, ProvinceServiceAuditable
                                           provinceServiceAuditable, RegionServiceAuditable regionServiceAuditable,
                                           DistrictServiceAuditable districtServiceAuditable,
                                           AssemblyServiceAuditable assemblyServiceAuditable, MessageService messageService) {
        return new ProvinceServiceImpl(provinceServiceValidator, provinceRepository, regionRepository, districtRepository,
                assemblyRepository, modelMapper, provinceServiceAuditable, regionServiceAuditable, districtServiceAuditable,
                assemblyServiceAuditable, messageService);
    }

    @Bean
    public DistrictServiceAuditable districtServiceAuditable(DistrictRepository districtRepository){
        return new DistrictServiceAuditableImpl(districtRepository);
    }

    @Bean
    public DistrictServiceValidator districtServiceValidator(){
        return new DistrictServiceValidatorImpl();
    }

    @Bean
    public DistrictService districtService(DistrictServiceValidator districtServiceValidator, DistrictRepository
            districtRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository, AssemblyRepository
            assemblyRepository, ModelMapper modelMapper, DistrictServiceAuditable
                                                   districtServiceAuditable, AssemblyServiceAuditable assemblyServiceAuditable,
                                           MessageService messageService) {
        return new DistrictServiceImpl(districtServiceValidator, districtRepository, provinceRepository, regionRepository,
                assemblyRepository, modelMapper, districtServiceAuditable, assemblyServiceAuditable, messageService);
    }

    @Bean
    public RegionServiceAuditable regionServiceAuditable(RegionRepository regionRepository){
        return new RegionServiceAuditableImpl(regionRepository);
    }

    @Bean
    public RegionServiceValidator regionServiceValidator(){
        return new RegionServiceValidatorImpl();
    }

    @Bean
    public RegionService regionService(RegionServiceValidator regionServiceValidator, RegionRepository regionRepository,
                                       ProvinceRepository provinceRepository, DistrictRepository districtRepository,
                                       AssemblyRepository assemblyRepository, ModelMapper modelMapper, RegionServiceAuditable
                                                   regionServiceAuditable, DistrictServiceAuditable districtServiceAuditable,
                                       AssemblyServiceAuditable assemblyServiceAuditable, MessageService messageService) {
        return new RegionServiceImpl(regionServiceValidator, regionRepository, districtRepository,
                assemblyRepository, provinceRepository, modelMapper, regionServiceAuditable, districtServiceAuditable, assemblyServiceAuditable,
                messageService);
    }

    @Bean
    public AssemblyServiceAuditable assemblyServiceAuditable(AssemblyRepository assemblyRepository){
        return new AssemblyServiceAuditableImpl(assemblyRepository);
    }

    @Bean
    public AssemblyServiceValidator assemblyServiceValidator(){
        return new AssemblyServiceValidatorImpl();
    }

    @Bean
    public AccountServiceAuditable accountServiceAuditable(AccountRepository accountRepository){
        return new AccountServiceAuditableImpl(accountRepository);
    }

    @Bean
    public AssemblyService assemblyService(AssemblyServiceValidator assemblyServiceValidator, AssemblyRepository
            assemblyRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository,
                                           RegionRepository regionRepository, UserAccountRepository userAccountRepository,
                                           AccountRepository accountRepository,
                                           ModelMapper modelMapper,
                                           AssemblyServiceAuditable assemblyServiceAuditable,
                                           AccountServiceAuditable accountServiceAuditable, MessageService
                                                       messageService, AccountService accountService) {
        return new AssemblyServiceImpl(assemblyServiceValidator, assemblyRepository, districtRepository,
                provinceRepository, regionRepository, userAccountRepository, accountRepository, modelMapper, assemblyServiceAuditable,
                accountServiceAuditable, messageService, accountService);
    }

    @Bean
    public AccountServiceValidator accountServiceValidator(){
        return new AccountServiceValidatorImpl();
    }

    @Bean
    public AccountService accountService(AccountServiceValidator accountServiceValidator, AccountServiceAuditable accountServiceAuditable,
                                         AccountRepository accountRepository, AssemblyRepository assemblyRepository,
                                         UserAccountRepository userAccountRepository, ModelMapper modelMapper,
                                         MessageService messageService){
        return new AccountServiceImpl(accountServiceValidator, accountServiceAuditable, accountRepository,
                assemblyRepository, userAccountRepository, modelMapper, messageService);
    }

    @Bean
    public PaymentService paymentService(PaymentServiceValidator paymentServiceValidator, PaymentRepository paymentRepository,
                                         AssemblyRepository assemblyRepository, UserAccountRepository userAccountRepository,
                                         AccountRepository accountRepository, ModelMapper modelMapper, PaymentServiceAuditable
                                                     paymentServiceAuditable, MessageService messageService, AccountService
                                                     accountService){
        return new PaymentServiceImpl(paymentServiceValidator, paymentRepository, assemblyRepository,
                userAccountRepository, accountRepository, modelMapper, paymentServiceAuditable, messageService, accountService);
    }

    @Bean
    public PaymentServiceValidator paymentServiceValidator(){
        return new PaymentServiceValidatorImpl();
    }

    @Bean
    public PaymentServiceAuditable paymentServiceAuditable(PaymentRepository paymentRepository){
        return new PaymentServiceAuditableImpl(paymentRepository);
    }

    @Bean
    public UserAccountServiceValidator userAccountServiceValidator(){
        return new UserAccountServiceValidatorImpl();
    }

    @Bean
    public UserAccountServiceAuditable userAccountServiceAuditable(UserAccountRepository userAccountRepository){
        return new UserAccountServiceAuditableImpl(userAccountRepository);
    }

    @Bean
    public UserAccountService userAccountService(UserAccountServiceValidator userAccountServiceValidator, UserAccountRepository
            userAccountRepository, UserGroupRepository userGroupRepository, AssemblyRepository assemblyRepository,
                                                 ModelMapper modelMapper, UserAccountServiceAuditable userAccountServiceAuditable,
                                                 MessageService messageService){
        return new UserAccountServiceImpl(userAccountServiceValidator, userAccountRepository, userGroupRepository,
              assemblyRepository, modelMapper, userAccountServiceAuditable, messageService);
    }

    @Bean
    public UserGroupServiceValidator userGroupServiceValidator(){ return new UserGroupServiceValidatorImpl() {}; }

    @Bean
    public UserGroupServiceAuditable userGroupServiceAuditable(UserGroupRepository userGroupRepository){
        return new UserGroupServiceAuditableImpl(userGroupRepository);
    }

    @Bean
    public UserGroupService userGroupService(UserGroupServiceValidator userGroupServiceValidator, UserGroupRepository userGroupRepository,
                                             ModelMapper modelMapper, UserGroupServiceAuditable userGroupServiceAuditable,
                                             MessageService messageService){
        return new UserGroupServiceImpl(userGroupServiceValidator, userGroupRepository, modelMapper,
                userGroupServiceAuditable, messageService);
    }

    @Bean
    public UserRoleServiceValidator userRoleServiceValidator(){ return new UserRoleServiceValidatorImpl() {}; }

    @Bean
    public UserRoleServiceAuditable userRoleServiceAuditable(UserRoleRepository userRoleRepository){
        return new UserRoleServiceAuditableImpl(userRoleRepository);
    }

    @Bean
    public UserRoleService userRoleService(UserRoleServiceValidator userGroupServiceValidator, UserRoleRepository userRoleRepository,
                                            ModelMapper modelMapper, UserRoleServiceAuditable userRoleServiceAuditable,
                                            MessageService messageService){
        return new UserRoleServiceImpl(userGroupServiceValidator, userRoleRepository, modelMapper,
                userRoleServiceAuditable, messageService);
    }
}
