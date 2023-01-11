package com.professionallawnservices.app.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelpers {


    public SecurityHelpers() {

    }

    public Authentication getUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
