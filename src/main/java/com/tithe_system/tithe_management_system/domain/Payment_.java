package com.tithe_system.tithe_management_system.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public class Payment_ {
    public static volatile SingularAttribute<Payment, Long> id;
    public static volatile SingularAttribute<Payment, String> transactionReference;
    public static volatile SingularAttribute<Payment, String> accountNumber;
    public static volatile SingularAttribute<Payment, String> narration;
    public static volatile SingularAttribute<Payment, String> paymentChannel;
    public static volatile SingularAttribute<Payment, String> paymentType;
    public static volatile SingularAttribute<Payment, String> currency;
    public static volatile SingularAttribute<Payment, String> paymentMethod;
    public static volatile SingularAttribute<Payment, String> paymentStatus;
    public static volatile SingularAttribute<Payment, String> entityStatus;

}
