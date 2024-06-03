package com.tithe_system.tithe_management_system.service.config;

import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.business.logic.api.DistrictService;
import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.business.logic.api.ProvinceService;
import com.tithe_system.tithe_management_system.business.logic.api.RegionService;
import com.tithe_system.tithe_management_system.business.logic.api.UserAccountService;
import com.tithe_system.tithe_management_system.business.logic.api.UserGroupService;
import com.tithe_system.tithe_management_system.business.logic.api.UserRoleService;
import com.tithe_system.tithe_management_system.service.processor.api.AssemblyServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.DistrictServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.PaymentServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.ProvinceServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.RegionServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.UserAccountServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.UserGroupServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.api.UserRoleServiceProcessor;
import com.tithe_system.tithe_management_system.service.processor.impl.AssemblyServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.DistrictServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.PaymentServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.ProvinceServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.RegionServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.UserAccountServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.UserGroupServiceProcessorImpl;
import com.tithe_system.tithe_management_system.service.processor.impl.UserRoleServiceProcessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public DistrictServiceProcessor districtServiceProcessor(DistrictService districtService) {
        return new DistrictServiceProcessorImpl(districtService);
    }

    @Bean
    public ProvinceServiceProcessor provinceServiceProcessor(ProvinceService provinceService) {
        return new ProvinceServiceProcessorImpl(provinceService);
    }

    @Bean
    public RegionServiceProcessor regionServiceProcessor(RegionService regionService) {
        return new RegionServiceProcessorImpl(regionService);
    }

    @Bean
    public AssemblyServiceProcessor assemblyServiceProcessor(AssemblyService assemblyService) {
        return new AssemblyServiceProcessorImpl(assemblyService);
    }

    @Bean
    public PaymentServiceProcessor paymentServiceProcessor(PaymentService paymentService) {
        return new PaymentServiceProcessorImpl(paymentService);
    }
    @Bean
    public UserAccountServiceProcessor userAccountServiceProcessor(UserAccountService userAccountService) {
        return new UserAccountServiceProcessorImpl(userAccountService);
    }

    @Bean
    public UserGroupServiceProcessor userGroupServiceProcessor(UserGroupService userGroupService) {
        return new UserGroupServiceProcessorImpl(userGroupService);
    }

    @Bean
    public UserRoleServiceProcessor userRoleServiceProcessor(UserRoleService userRoleService) {
        return new UserRoleServiceProcessorImpl(userRoleService);
    }
}
