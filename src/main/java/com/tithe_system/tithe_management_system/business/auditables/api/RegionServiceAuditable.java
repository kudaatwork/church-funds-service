package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.Region;

import java.util.List;
import java.util.Locale;

public interface RegionServiceAuditable {
    Region create(Region region, Locale locale, String username);
    Region edit(Region region, Locale locale, String username);
    Region delete(Region region, Locale locale);
    List<Region> deleteAll(List<Region> regionList, Locale locale);
}
