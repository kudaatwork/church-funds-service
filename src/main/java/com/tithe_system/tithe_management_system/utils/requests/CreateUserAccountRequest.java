package com.tithe_system.tithe_management_system.utils.requests;

public class CreateUserAccountRequest {

    private String firstName;
    private String lastName;
    private String gender;
    private String title;
    private String phoneNumber;
    private String emailAddress;
    private String username;
    private String password;
    private Long userGroupId;
    private Long assemblyId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Long getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Long assemblyId) {
        this.assemblyId = assemblyId;
    }

    @Override
    public String toString() {
        return "CreateUserAccountRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", title='" + title + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userGroupId=" + userGroupId +
                ", assemblyId=" + assemblyId +
                '}';
    }
}
