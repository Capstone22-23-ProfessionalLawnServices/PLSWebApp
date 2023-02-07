package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.enums.RolesEnum;

import javax.validation.constraints.Pattern;

public class UserRequest {

    @Pattern(regexp = "^[a-z0-9_-]{3,16}$")
    private String username;

    @Pattern(regexp = "(?=(.*[0-9]))((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z]))^.{8,}$")
    private String password;

    @Pattern(regexp = "(?=(.*[0-9]))((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z]))^.{8,}$")
    private String newPassword;

    private String role;

    private RolesEnum rolesEnum;

    public UserRequest() {

    }

    public UserRequest(String username, String role, RolesEnum rolesEnum) {
        this.username = username;
        this.role = role;
        this.rolesEnum = rolesEnum;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RolesEnum getRolesEnum() {
        return rolesEnum;
    }

    public void setRolesEnum(RolesEnum rolesEnum) {
        this.rolesEnum = rolesEnum;
    }
}
