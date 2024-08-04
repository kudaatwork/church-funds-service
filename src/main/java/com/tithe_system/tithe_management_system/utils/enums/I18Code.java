package com.tithe_system.tithe_management_system.utils.enums;

public enum I18Code {
    MESSAGE_CREATE_PROVINCE_INVALID_REQUEST("message.create.province.invalid.request"),
    MESSAGE_PROVINCE_ALREADY_EXISTS("message.province.already.exists"),
    MESSAGE_PROVINCE_CREATED_SUCCESSFULLY("message.province.created.successfully"),
    MESSAGE_EDIT_PROVINCE_INVALID_REQUEST("message.edit.province.invalid.request"),
    MESSAGE_PROVINCE_DOES_NOT_EXIST("message.province.does.not.exist"),
    MESSAGE_PROVINCE_EDITED_SUCCESSFULLY("message.province.edited.successfully"),
    MESSAGE_INVALID_PROVINCE_ID_SUPPLIED("message.invalid.province.id.supplied"),
    MESSAGE_PROVINCE_NOT_FOUND("message.province.not.found"),
    MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY("message.province.retrieved.successfully"),
    MESSAGE_PROVINCE_DELETED_SUCCESSFULLY("message.province.deleted.successfully"),

    MESSAGE_CREATE_DISTRICT_INVALID_REQUEST("message.create.district.invalid.request"),
    MESSAGE_DISTRICT_ALREADY_EXISTS("message.district.already.exists"),
    MESSAGE_DISTRICT_CREATED_SUCCESSFULLY("message.district.created.successfully"),
    MESSAGE_EDIT_DISTRICT_INVALID_REQUEST("message.edit.district.invalid.request"),
    MESSAGE_DISTRICT_DOES_NOT_EXIST("message.district.does.not.exist"),
    MESSAGE_DISTRICT_EDITED_SUCCESSFULLY("message.district.edited.successfully"),
    MESSAGE_INVALID_DISTRICT_ID_SUPPLIED("message.invalid.district.id.supplied"),
    MESSAGE_DISTRICT_DELETED_SUCCESSFULLY("message.district.deleted.successfully"),
    MESSAGE_DISTRICT_NOT_FOUND("message.district.not.found"),
    MESSAGE_DELETE_ERROR_DISTRICT_NOT_FOUND("message.delete.error.district.not.found"),
    MESSAGE_DISTRICT_RETRIEVED_SUCCESSFULLY("message.district.retrieved.successfully"),

    MESSAGE_REGION_ALREADY_EXISTS("message.region.already.exists"),
    MESSAGE_REGION_NOT_FOUND("message.region.not.found"),
    MESSAGE_CREATE_REGION_INVALID_REQUEST("message.create.region.invalid.request"),
    MESSAGE_REGION_CREATED_SUCCESSFULLY("message.region.created.successfully"),
    MESSAGE_EDIT_REGION_INVALID_REQUEST("message.edit.region.invalid.request"),
    MESSAGE_INVALID_REGION_ID_SUPPLIED("message.invalid.region.id.supplied"),
    MESSAGE_REGION_DELETED_SUCCESSFULLY("message.region.deleted.successfully"),
    MESSAGE_REGION_RETRIEVED_SUCCESSFULLY("message.region.retrieved.successfully"),
    MESSAGE_REGION_EDITED_SUCCESSFULLY("message.region.edited.successfully"),

    MESSAGE_CREATE_ASSEMBLY_INVALID_REQUEST("message.create.assembly.invalid.request"),
    MESSAGE_ASSEMBLY_ALREADY_EXISTS("message.assembly.already.exists"),
    MESSAGE_ASSEMBLY_CREATED_SUCCESSFULLY("message.assembly.created.successfully"),
    MESSAGE_EDIT_ASSEMBLY_INVALID_REQUEST("message.edit.assembly.invalid.request"),
    MESSAGE_ASSEMBLY_EDITED_SUCCESSFULLY("message.assembly.edited.successfully"),
    MESSAGE_INVALID_ASSEMBLY_ID_SUPPLIED("message.invalid.assembly.id.supplied"),
    MESSAGE_ASSEMBLY_DOES_NOT_EXIST("message.assembly.does.not.exist"),
    MESSAGE_ASSEMBLY_NOT_FOUND("message.assembly.not.found"),
    MESSAGE_ASSEMBLY_DELETED_SUCCESSFULLY("message.assembly.deleted.successfully"),
    MESSAGE_ASSEMBLY_RETRIEVED_SUCCESSFULLY("message.assembly.retrieved.successfully"),

    MESSAGE_CREATE_PAYMENT_INVALID_REQUEST("message.create.payment.invalid.request"),
    MESSAGE_PAYMENT_INITIATED_SUCCESSFULLY("message.payment.initiated.successfully"),
    MESSAGE_REVERSE_PAYMENT_INVALID_REQUEST("message.reverse.payment.invalid.request"),
    MESSAGE_PAYMENT_NOT_FOUND("message.payment.not.found"),
    MESSAGE_PAYMENT_REVERSED_SUCCESSFULLY("message.payment.reversed.successfully"),
    MESSAGE_INVALID_PAYMENT_ID_SUPPLIED("message.invalid.payment.id.supplied"),
    MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY("message.payment.retrieved.successfully"),
    MESSAGE_CHANGE_PAYMENT_STATUS_INVALID_REQUEST("message.change.payment.status.invalid.request"),
    MESSAGE_CANNOT_CHANGE_PAYMENT_STATUS("message.cannot.change.payment.status"),
    MESSAGE_PAYMENT_STATUS_CHANGED_SUCCESSFULLY("message.payment.status.changed.successfully"),

    MESSAGE_CREATE_ACCOUNT_INVALID_REQUEST("message.create.account.invalid.request"),
    MESSAGE_ACCOUNT_DOES_NOT_EXIST("message.account.does.not.exist"),
    MESSAGE_ACCOUNT_UPDATED_SUCCESSFULLY("message.account.updated.successfully"),
    MESSAGE_ACCOUNT_NOT_FOUND("message.account.not.found"),
    MESSAGE_UPDATE_ACCOUNT_INVALID_REQUEST("message.update.account.invalid.request"),
    MESSAGE_ACCOUNT_ALREADY_EXISTS("message.account.already.exists"),
    MESSAGE_ACCOUNT_CREATED_SUCCESSFULLY("message.account.created.successfully"),
    MESSAGE_INVALID_ACCOUNT_ID_SUPPLIED("message.invalid.account.id.supplied"),
    MESSAGE_ACCOUNT_RETRIEVED_SUCCESSFULLY("message.account.retrieved.successfully"),

    MESSAGE_USER_ACCOUNT_NOT_FOUND("message.user.account.not.found"),
    MESSAGE_CREATE_USER_ACCOUNT_INVALID_REQUEST("message.create.user.account.invalid.request"),
    MESSAGE_USER_ACCOUNT_ALREADY_EXISTS("message.user.account.already.exists"),
    MESSAGE_USER_ACCOUNT_CREATED_SUCCESSFULLY("message.user.account.created.successfully"),
    MESSAGE_EDIT_USER_ACCOUNT_INVALID_REQUEST("message.edit.user.account.invalid.request"),
    MESSAGE_USER_ACCOUNT_EDITED_SUCCESSFULLY("message.user.account.edited.successfully"),
    MESSAGE_INVALID_USER_ACCOUNT_ID_SUPPLIED("message.invalid.user.account.id.supplied"),
    MESSAGE_USER_ACCOUNT_DOES_NOT_EXIST("message.user.account.does.not.exist"),
    MESSAGE_USER_ACCOUNT_DELETED_SUCCESSFULLY("message.user.account.deleted.successfully"),
    MESSAGE_USER_ACCOUNT_RETRIEVED_SUCCESSFULLY("message.user.account.retrieved.successfully"),

    MESSAGE_USER_GROUP_ALREADY_EXISTS("message.user.group.already.exists"),
    MESSAGE_CREATE_USER_GROUP_INVALID_REQUEST("message.create.user.group.invalid.request"),
    MESSAGE_USER_GROUP_CREATED_SUCCESSFULLY("message.user.group.created.successfully"),
    MESSAGE_EDIT_USER_GROUP_INVALID_REQUEST("message.edit.user.group.invalid.request"),
    MESSAGE_USER_GROUP_NOT_FOUND("message.user.group.not.found"),
    MESSAGE_USER_GROUP_EDITED_SUCCESSFULLY("message.user.group.edited.successfully"),
    MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED("message.invalid.user.group.id.supplied"),
    MESSAGE_USER_GROUP_DOES_NOT_EXIST("message.user.group.does.not.exist"),
    MESSAGE_USER_GROUP_DELETED_SUCCESSFULLY("message.user.group.deleted.successfully"),
    MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY("message.user.group.retrieved.successfully"),

    MESSAGE_CREATE_USER_ROLE_INVALID_REQUEST("message.create.user.role.invalid.request"),
    MESSAGE_USER_ROLE_ALREADY_EXISTS("message.user.role.already.exists"),
    MESSAGE_USER_ROLE_CREATED_SUCCESSFULLY("message.user.role.created.successfully"),
    MESSAGE_EDIT_USER_ROLE_INVALID_REQUEST("message.edit.user.role.invalid.request"),
    MESSAGE_USER_ROLE_NOT_FOUND("message.user.role.not.found"),
    MESSAGE_USER_ROLE_EDITED_SUCCESSFULLY("message.user.role.edited.successfully"),
    MESSAGE_INVALID_USER_ROLE_ID_SUPPLIED("message.invalid.user.role.id.supplied"),
    MESSAGE_USER_ROLE_DOES_NOT_EXIST("message.user.role.does.not.exist"),
    MESSAGE_USER_ROLE_DELETED_SUCCESSFULLY("message.user.role.deleted.successfully"),
    MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY("message.user.role.retrieved.successfully"),
    MESSAGE_INVALID_ASSIGN_USER_ROLE_TO_USER_GROUP_REQUEST("message.invalid.assign.user.role.to.user.group.request"),
    MESSAGE_USER_ROLES_ALREADY_ASSIGNED("message.user.roles.already.assigned"),
    MESSAGE_USER_ROLE_ASSIGNED_SUCCESSFULLY("message.user.role.assigned.successfully");
    private final String code;

    I18Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
