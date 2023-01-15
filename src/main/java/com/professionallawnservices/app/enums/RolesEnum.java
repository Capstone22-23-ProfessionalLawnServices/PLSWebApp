package com.professionallawnservices.app.enums;

/*
These are the roles that an account can have in enum form. They are different from the role that is used to create an
account because the framework prepends ROLE_ to the role name and upper cases the entire role name.
 */

public enum RolesEnum {
    OWNER("ROLE_OWNER", 3),
    MANAGER("ROLE_MANAGER", 2),
    EMPLOYEE("ROLE_EMPLOYEE", 1),
    USER("ROLE_USER", 0);

    final public String roleName;
    final public int accessLevel;

    RolesEnum (String roleName, int accessLevel) {
        this.roleName = roleName;
        this.accessLevel = accessLevel;
    }

}
