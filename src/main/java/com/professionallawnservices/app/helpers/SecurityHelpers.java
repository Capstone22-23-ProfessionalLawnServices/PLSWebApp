package com.professionallawnservices.app.helpers;

import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.professionallawnservices.app.enums.RolesEnum;

import java.util.*;

public class SecurityHelpers {


    public SecurityHelpers() {

    }

    public static Authentication getUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Collection<GrantedAuthority> getUserRoles(){
        return (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public static ArrayList<String> getUserRolesList(){
        Collection<GrantedAuthority> userRoles = getUserRoles();
        ArrayList<String> userRoleList = new ArrayList<>();

        for (GrantedAuthority role:
             userRoles) {
            userRoleList.add(role.toString());
        }

        return userRoleList;
    }

    public static RolesEnum getPrimaryUserRole(){

        ArrayList<String> userRoles = getUserRolesList();
        Set<RolesEnum> allRoles = EnumSet.allOf(RolesEnum.class);

        RolesEnum primaryRole = RolesEnum.USER;


        for (String userRole:
             userRoles) {
            for (RolesEnum role:
                 allRoles) {

                if ((userRole.equals(role.authorityName)) && (primaryRole.accessLevel <= role.accessLevel)) {
                    primaryRole = role;
                }
            }
        }

        return primaryRole;
    }

}
