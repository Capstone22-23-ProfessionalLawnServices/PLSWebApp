package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.models.Contacts;
import com.professionallawnservices.app.models.Customer;
import com.professionallawnservices.app.repos.ContactsRepo;
import com.professionallawnservices.app.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class ContactsController {

    @Autowired
    ContactsRepo contactsRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping(value = "/getContacts")
    //@PreAuthorize("hasRole('MANAGER')")
    public String getContacts(Model model) {
        var x = contactsRepo.findAll().toString();
        model.addAttribute("contactsList", x);
        return "contactstest";
    }


}
