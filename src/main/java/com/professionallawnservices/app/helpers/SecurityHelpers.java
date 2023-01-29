package com.professionallawnservices.app.helpers;

import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static String encode(String rawString) {
        return Base64.getEncoder().encodeToString(rawString.getBytes());
    }

    public static String decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
