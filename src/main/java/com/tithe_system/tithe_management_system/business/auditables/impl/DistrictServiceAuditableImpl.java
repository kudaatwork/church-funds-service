package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.utils.trackers.AuditCreateEvent;
import com.tithe_system.tithe_management_system.utils.trackers.AuditDeleteEvent;
import com.tithe_system.tithe_management_system.utils.trackers.AuditEditEvent;

import java.util.List;
import java.util.Locale;

public class DistrictServiceAuditableImpl implements DistrictServiceAuditable {

    private final DistrictRepository districtRepository;

    public DistrictServiceAuditableImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @AuditCreateEvent
    @Override
    public District create(District district, Locale locale, String username) {

        return districtRepository.save(district);
    }

    @AuditEditEvent
    @Override
    public District edit(District district, Locale locale, String username) {

        return districtRepository.save(district);
    }

    @AuditDeleteEvent
    @Override
    public District delete(District district, Locale locale) {

        return districtRepository.save(district);
    }

    @Override
    public List<District> deleteAll(List<District> districtList, Locale locale) {

        return districtRepository.saveAll(districtList);
    }
}
