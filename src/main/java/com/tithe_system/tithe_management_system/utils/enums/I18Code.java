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

    MESSAGE_USER_ACCOUNT_NOT_FOUND("message.user.account.not.found"),

    MESSAGE_CREATE_PAYMENT_INVALID_REQUEST("message.create.payment.invalid.request"),
    MESSAGE_PAYMENT_INITIATED_SUCCESSFULLY("message.payment.initiated.successfully"),
    MESSAGE_CREATE_ACCOUNT_INVALID_REQUEST("message.create.account.invalid.request");
    private final String code;

    I18Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
