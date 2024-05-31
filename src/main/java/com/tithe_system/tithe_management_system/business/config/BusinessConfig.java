package com.tithe_system.tithe_management_system.business.config;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.ProvinceServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.RegionServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.impl.AccountServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.AssemblyServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.DistrictServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.ProvinceServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.auditables.impl.RegionServiceAuditableImpl;
import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.business.logic.api.DistrictService;
import com.tithe_system.tithe_management_system.business.logic.api.ProvinceService;
import com.tithe_system.tithe_management_system.business.logic.api.RegionService;
import com.tithe_system.tithe_management_system.business.logic.impl.AssemblyServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.DistrictServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.ProvinceServiceImpl;
import com.tithe_system.tithe_management_system.business.logic.impl.RegionServiceImpl;
import com.tithe_system.tithe_management_system.business.validations.api.AssemblyServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.DistrictServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.ProvinceServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.api.RegionServiceValidator;
import com.tithe_system.tithe_management_system.business.validations.impl.AssemblyServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.DistrictServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.ProvinceServiceValidatorImpl;
import com.tithe_system.tithe_management_system.business.validations.impl.RegionServiceValidatorImpl;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
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
                                           ModelMapper modelMapper,
                                           AssemblyServiceAuditable assemblyServiceAuditable,
                                           AccountServiceAuditable accountServiceAuditable, MessageService
                                                       messageService) {
        return new AssemblyServiceImpl(assemblyServiceValidator, assemblyRepository, districtRepository,
                provinceRepository, regionRepository, userAccountRepository, modelMapper, assemblyServiceAuditable, accountServiceAuditable, messageService);
    }
}
