package com.tithe_system.tithe_management_system.utils.requests;

public class AssemblyMultipleFiltersRequest extends DataTableRequest{
    private String name;
    private String contactPhoneNumber;
    private String contactEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "AssemblyMultipleFiltersRequest{" +
                "name='" + name + '\'' +
                ", contactPhoneNumber='" + contactPhoneNumber + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
