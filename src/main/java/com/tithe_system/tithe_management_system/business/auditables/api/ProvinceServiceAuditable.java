package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.Province;

import java.util.List;
import java.util.Locale;

public interface ProvinceServiceAuditable {
    Province create(Province province, Locale locale, String username);
    Province edit(Province province, Locale locale, String username);
    Province delete(Province province, Locale locale);
    List<Province> deleteAll(List<Province> provinceList, Locale locale);
}
