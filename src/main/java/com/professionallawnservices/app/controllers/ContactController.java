package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.models.Contacts;
import com.professionallawnservices.app.repos.ContactsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class ContactController {

    @Autowired
    ContactsRepo contactsRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/contacts")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "contacts";
    }

    @GetMapping(value = "/getContacts")
    //@PreAuthorize("hasRole('MANAGER')")
    public String getContacts(Model model) {
        var x = contactsRepo.findAll().toString();
        model.addAttribute("contactsList", x);
        return "contactstest";
    }

    //Test insert for contact
    @GetMapping (value = "/insertContact")
    @PreAuthorize("hasRole('MANAGER')")
    public void insertContact() {
        Contacts contact = new Contacts();
        contact.setContactEmail("test@test.com");
        contact.setContactName("Jordan hinson");
        contact.setContactPhone("9999999");
        contactsRepo.save(contact);
    }

}