package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Account_;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(Account_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<Account> accountNumberLike(final String accountNumber) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Account_.accountNumber).as(String.class), accountNumber + "%");
            return p;
        };
    }

    public static Specification<Account> transactionReferenceLike(final String transactionReference) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Account_.transactionReference).as(String.class), transactionReference + "%");
            return p;
        };
    }

    public static Specification<Account> nameLike(final String name) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Account_.name).as(String.class), name + "%");
            return p;
        };
    }

    public static Specification<Account> narrationIn(final List<Narration> narrationList) {
        return (root, query, cb) -> root.get(Account_.narration).in(narrationList);
    }

    public static Specification<Account> currencyIn(final List<Currency> currencyList) {
        return (root, query, cb) -> root.get(Account_.currency).in(currencyList);
    }

    public static Specification<Account> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(
                    cb.like(root.get(Account_.transactionReference), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(Account_.accountNumber), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(Account_.name), "%" + search.toUpperCase() + "%")
            );

            try {

                Narration narration = Narration.valueOf(search);
                Currency currency = Currency.valueOf(search);

                p = cb.or(p, cb.equal(root.get(Account_.narration), narration),
                        cb.equal(root.get(Account_.currency), currency));
            }
            catch (Exception e) {

                p = cb.or(
                        cb.like(root.get(Account_.transactionReference), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(Account_.accountNumber), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(Account_.name), "%" + search.toUpperCase() + "%"));
            }

            return p;
        };
    }
}
