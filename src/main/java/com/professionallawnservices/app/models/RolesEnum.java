package com.professionallawnservices.app.models;

/*
These are the roles that an account can have in enum form. They are different from the role that is used to create an
account because the framework prepends ROLE_ to the role name and upper cases the entire role name.
 */

public enum RolesEnum {
    MANAGER("ROLE_MANAGER"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    USER("ROLE_USER");

    final public String roleName;

    RolesEnum (String roleName) {
        this.roleName = roleName;
    }

}
