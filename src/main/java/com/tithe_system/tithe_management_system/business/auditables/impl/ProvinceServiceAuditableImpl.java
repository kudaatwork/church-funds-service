package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.ProvinceServiceAuditable;
import com.tithe_system.tithe_management_system.domain.Province;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.utils.trackers.AuditCreateEvent;
import com.tithe_system.tithe_management_system.utils.trackers.AuditDeleteEvent;
import com.tithe_system.tithe_management_system.utils.trackers.AuditEditEvent;

import java.util.List;
import java.util.Locale;

public class ProvinceServiceAuditableImpl implements ProvinceServiceAuditable {
    private final ProvinceRepository provinceRepository;

    public ProvinceServiceAuditableImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @AuditCreateEvent
    @Override
    public Province create(Province province, Locale locale, String username) {

        return provinceRepository.save(province);
    }

    @AuditEditEvent
    @Override
    public Province edit(Province province, Locale locale, String username) {

        return provinceRepository.save(province);
    }

    @AuditDeleteEvent
    @Override
    public Province delete(Province province, Locale locale) {

        return provinceRepository.save(province);
    }
}
