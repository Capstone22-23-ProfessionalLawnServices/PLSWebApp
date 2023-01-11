package com.professionallawnservices.app.controller;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.RolesEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;

@Controller
public class HomeController {

    final private SecurityHelpers securityHelpers = new SecurityHelpers();

    //@GetMapping(value = {"/home", "/"})
    @GetMapping("/home")
    public String viewHome(@RequestParam(name="role", defaultValue="ROLE_EMPLOYEE") String role, Model model) {
        model.addAttribute("role", role);
        model.addAttribute("roleManager", RolesEnum.MANAGER.roleName);

        return "home";
    }

    /*
    Redirects to the home template view. Appends the user's first listed authority, or role, to the url. The first
    role is assumed to be the most significant and should have the highest level of access.
     */

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        Authentication user = securityHelpers.getUser();
        Collection<GrantedAuthority> userRolesCollection = (Collection<GrantedAuthority>) user.getAuthorities();

        String mainRole = userRolesCollection.toArray()[0].toString();
        
        attributes.addAttribute("role", mainRole);
        return new RedirectView("/home");
    }

}