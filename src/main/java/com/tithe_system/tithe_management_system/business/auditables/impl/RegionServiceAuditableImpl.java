package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.RegionServiceAuditable;
import com.tithe_system.tithe_management_system.domain.Region;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import java.util.List;
import java.util.Locale;

public class RegionServiceAuditableImpl implements RegionServiceAuditable {

    private final RegionRepository regionRepository;

    public RegionServiceAuditableImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region create(Region region, Locale locale, String username) {

        return regionRepository.save(region);
    }

    @Override
    public Region edit(Region region, Locale locale, String username) {

        return regionRepository.save(region);
    }

    @Override
    public Region delete(Region region, Locale locale) {

        return regionRepository.save(region);
    }

    @Override
    public List<Region> deleteAll(List<Region> regionList, Locale locale) {
        return regionRepository.saveAll(regionList);
    }
}
