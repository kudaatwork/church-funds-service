package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.domain.PaymentChannel;
import com.tithe_system.tithe_management_system.domain.PaymentMethod;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.PaymentType;
import com.tithe_system.tithe_management_system.domain.Payment_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class PaymentSpecification {
    public static Specification<Payment> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(Payment_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<Payment> transactionReferenceLike(final String transactionReference) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Payment_.transactionReference).as(String.class), transactionReference + "%");
            return p;
        };
    }

    public static Specification<Payment> accountNumberLike(final String accountNumber) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Payment_.accountNumber).as(String.class), accountNumber + "%");
            return p;
        };
    }

    public static Specification<Payment> narrationIn(final List<Narration> narrationList) {
        return (root, query, cb) -> root.get(Payment_.narration).in(narrationList);
    }

    public static Specification<Payment> paymentChannelIn(final List<PaymentChannel> paymentChannelList) {
        return (root, query, cb) -> root.get(Payment_.paymentChannel).in(paymentChannelList);
    }

    public static Specification<Payment> paymentTypeIn(final List<PaymentType> paymentTypeList) {
        return (root, query, cb) -> root.get(Payment_.paymentType).in(paymentTypeList);
    }

    public static Specification<Payment> currencyIn(final List<Currency> currencyList) {
        return (root, query, cb) -> root.get(Payment_.currency).in(currencyList);
    }

    public static Specification<Payment> paymentMethodIn(final List<PaymentMethod> paymentMethodList) {
        return (root, query, cb) -> root.get(Payment_.paymentMethod).in(paymentMethodList);
    }

    public static Specification<Payment> paymentStatusIn(final List<PaymentStatus> paymentStatusList) {
        return (root, query, cb) -> root.get(Payment_.paymentStatus).in(paymentStatusList);
    }

    public static Specification<Payment> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(
                    cb.like(root.get(Payment_.transactionReference), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(Payment_.accountNumber), "%" + search.toUpperCase() + "%")
            );

            try {

                Narration narration = Narration.valueOf(search);
                PaymentType paymentType = PaymentType.valueOf(search);
                PaymentChannel paymentChannel = PaymentChannel.valueOf(search);
                Currency currency = Currency.valueOf(search);
                PaymentMethod paymentMethod = PaymentMethod.valueOf(search);
                PaymentStatus paymentStatus = PaymentStatus.valueOf(search);

                p = cb.or(p, cb.equal(root.get(Payment_.narration), narration),
                             cb.equal(root.get(Payment_.paymentType), paymentType),
                             cb.equal(root.get(Payment_.paymentChannel), paymentChannel),
                             cb.equal(root.get(Payment_.currency), currency),
                             cb.equal(root.get(Payment_.paymentMethod), paymentMethod),
                             cb.equal(root.get(Payment_.paymentStatus), paymentStatus));
            }
            catch (Exception e) {

                p = cb.or(
                        cb.like(root.get(Payment_.transactionReference), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(Payment_.accountNumber), "%" + search.toUpperCase() + "%"));
            }

            return p;
        };
    }
}
