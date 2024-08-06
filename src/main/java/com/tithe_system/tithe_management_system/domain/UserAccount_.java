package com.tithe_system.tithe_management_system.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ {
    public static volatile SingularAttribute<UserAccount, String> firstName;
    public static volatile SingularAttribute<UserAccount, String> lastName;
    public static volatile SingularAttribute<UserAccount, String> gender;
    public static volatile SingularAttribute<UserAccount, String> title;
    public static volatile SingularAttribute<UserAccount, String> phoneNumber;
    public static volatile SingularAttribute<UserAccount, String> emailAddress;
    public static volatile SingularAttribute<UserAccount, String> username;
    public static volatile SingularAttribute<UserAccount, String> entityStatus;
}
