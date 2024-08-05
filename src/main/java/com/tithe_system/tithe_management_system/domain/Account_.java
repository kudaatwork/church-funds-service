package com.tithe_system.tithe_management_system.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public class Account_ {
    public static volatile SingularAttribute<Account, String> accountNumber;
    public static volatile SingularAttribute<Account, String> transactionReference;
    public static volatile SingularAttribute<Account, String> name;
    public static volatile SingularAttribute<Account, String> narration;
    public static volatile SingularAttribute<Account, String> currency;
    public static volatile SingularAttribute<Account, String> entityStatus;
}
