package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.District;
import java.util.List;
import java.util.Locale;

public interface DistrictServiceAuditable {
    District create(District district, Locale locale, String username);
    District edit(District district, Locale locale, String username);
    District delete(District district, Locale locale);
    List<District> deleteAll(List<District> districtList, Locale locale);
}
