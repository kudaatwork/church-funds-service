package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.Assembly;
import java.util.List;
import java.util.Locale;

public interface AssemblyServiceAuditable {
    Assembly create(Assembly assembly, Locale locale, String username);
    Assembly edit(Assembly assembly, Locale locale, String username);
    Assembly delete(Assembly assembly, Locale locale);
    List<Assembly> deleteAll(List<Assembly> assemblyList, Locale locale);
}
