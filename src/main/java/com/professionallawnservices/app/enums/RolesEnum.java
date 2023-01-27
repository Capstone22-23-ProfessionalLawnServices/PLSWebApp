package com.professionallawnservices.app.enums;

/*
These are the roles that an account can have in enum form. The account role has the value of the roleName. The account
authority has the value of the enum's authorityName.
 */

public enum RolesEnum {
    ADMIN("ADMIN", "ROLE_ADMIN", 4),
    OWNER("OWNER", "ROLE_OWNER", 3),
    MANAGER("MANAGER","ROLE_MANAGER", 2),
    EMPLOYEE("EMPLOYEE","ROLE_EMPLOYEE", 1),
    USER("USER","ROLE_USER", 0);

    final public String roleName;
    final public String authorityName;
    final public int accessLevel;

    RolesEnum (String roleName, String authorityName, int accessLevel) {
        this.roleName = roleName;
        this.authorityName = authorityName;
        this.accessLevel = accessLevel;
    }

}
