package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.models.Contact;
import com.professionallawnservices.app.repos.ContactsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/contactstest")
    public ResponseEntity<List<Contact>> test() {
        Contact contactRequest = new Contact();
        contactRequest.setContactId(1);
        return ResponseEntity.ok(contactsRepo.findAll());
    }


    @GetMapping("/search-contacts")
    public ResponseEntity<List<Contact>> getEmployeeByName(@RequestParam(name = "search") String search) {
        List<Contact> contacts = contactsRepo.findByContactName(search);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/search-contacts-post")
    public ResponseEntity<List<Contact>> getEmployeeByNamePost(@RequestBody Contact contact) {
        String contactName = contact.getContactName();
        List<Contact> contacts = contactsRepo.findByContactName(contactName);
        return ResponseEntity.ok(contacts);
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
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> insertContact() {
        Contact contact = new Contact();
        contact.setContactEmail("test@test.com");
        contact.setContactName("Test TEst");
        contact.setContactPhone("9999999");
        contactsRepo.save(contact);
        return ResponseEntity.ok("Contact inserted successfully");
    }

}