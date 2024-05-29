package com.tithe_system.tithe_management_system.repository.config;

import com.tithe_system.tithe_management_system.repository.RepositoryMarkerInterface;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.tithe_system.tithe_management_system.domain.DomainMarkerInterface;

@Configuration
@EnableJpaRepositories(basePackageClasses = {RepositoryMarkerInterface.class})
@EntityScan(basePackageClasses = {DomainMarkerInterface.class})
public class DataConfig {
}
