package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Gender;
import com.tithe_system.tithe_management_system.domain.Title;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.domain.UserAccount_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class UserAccountSpecification {

    public static Specification<UserAccount> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(UserAccount_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<UserAccount> firstNameLike(final String firstName) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(UserAccount_.firstName).as(String.class), firstName + "%");
            return p;
        };
    }

    public static Specification<UserAccount> lastNameLike(final String lastName) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(UserAccount_.lastName).as(String.class), lastName + "%");
            return p;
        };
    }

    public static Specification<UserAccount> genderIn(final List<Gender> genderList) {
        return (root, query, cb) -> root.get(UserAccount_.gender).in(genderList);
    }

    public static Specification<UserAccount> titleIn(final List<Title> titleList) {
        return (root, query, cb) -> root.get(UserAccount_.title).in(titleList);
    }

    public static Specification<UserAccount> phoneNumberLike(final String phoneNumber) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(UserAccount_.phoneNumber).as(String.class), phoneNumber + "%");
            return p;
        };
    }

    public static Specification<UserAccount> emailAddressLike(final String emailAddress) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(UserAccount_.emailAddress).as(String.class), emailAddress + "%");
            return p;
        };
    }

    public static Specification<UserAccount> usernameLike(final String username) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(UserAccount_.username).as(String.class), username + "%");
            return p;
        };
    }

    public static Specification<UserAccount> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(
                    cb.like(root.get(UserAccount_.firstName), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(UserAccount_.lastName), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(UserAccount_.phoneNumber), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(UserAccount_.emailAddress), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(UserAccount_.username), "%" + search.toUpperCase() + "%")
            );

            try {

                Gender gender = Gender.valueOf(search);
                Title title = Title.valueOf(search);

                p = cb.or(p, cb.equal(root.get(UserAccount_.gender), gender),
                        cb.equal(root.get(UserAccount_.title), title));
            }
            catch (Exception e) {

                p = cb.or(
                        cb.like(root.get(UserAccount_.firstName), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(UserAccount_.lastName), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(UserAccount_.phoneNumber), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(UserAccount_.emailAddress), "%" + search.toUpperCase() + "%"),
                        cb.like(root.get(UserAccount_.username), "%" + search.toUpperCase() + "%"));
            }

            return p;
        };
    }
}
