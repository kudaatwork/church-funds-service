package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;

import java.util.List;
import java.util.Locale;

public class AssemblyServiceAuditableImpl implements AssemblyServiceAuditable {
    private final AssemblyRepository assemblyRepository;

    public AssemblyServiceAuditableImpl(AssemblyRepository assemblyRepository) {
        this.assemblyRepository = assemblyRepository;
    }

    @Override
    public Assembly create(Assembly assembly, Locale locale, String username) {

        return assemblyRepository.save(assembly);
    }

    @Override
    public Assembly edit(Assembly assembly, Locale locale, String username) {

        return assemblyRepository.save(assembly);
    }

    @Override
    public Assembly delete(Assembly assembly, Locale locale) {

        return assemblyRepository.save(assembly);
    }

    @Override
    public List<Assembly> deleteAll(List<Assembly> assemblyList, Locale locale) {

        return assemblyRepository.saveAll(assemblyList);
    }
}
