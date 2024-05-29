package com.tithe_system.tithe_management_system.utils.aspects;

import com.tithe_system.tithe_management_system.domain.AuditTrail;
import com.tithe_system.tithe_management_system.domain.RecordAction;
import com.tithe_system.tithe_management_system.repository.AuditRepository;
import com.tithe_system.tithe_management_system.repository.config.DataConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import java.util.Locale;

@Aspect
@Configuration
@Import({DataConfig.class})
public class AuditDeleteAspect {

    private AuditRepository auditRepository;

    public AuditDeleteAspect(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Around("@annotation(com.tithe_system.tithe_management_system.utils.trackers.AuditDeleteEvent) && args(object, locale, username)")
    public Object around(ProceedingJoinPoint joinPoint, Object object, Locale locale, String username) throws Throwable {

        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setRecord(object.toString());
        auditTrail.setAction(RecordAction.DELETE.name());
        auditTrail.setActor(username);

        AuditTrail trailCreated = auditRepository.save(auditTrail);
        Object objectReturned = (Object) joinPoint.proceed();

        return objectReturned;
    }

}
