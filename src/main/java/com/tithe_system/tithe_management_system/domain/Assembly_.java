package com.tithe_system.tithe_management_system.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assembly.class)
public class Assembly_ {
    public static volatile SingularAttribute<Assembly, String> name;
    public static volatile SingularAttribute<Assembly, String> contactPhoneNumber;
    public static volatile SingularAttribute<Assembly, String> contactEmail;
    public static volatile SingularAttribute<Assembly, String> entityStatus;
}
