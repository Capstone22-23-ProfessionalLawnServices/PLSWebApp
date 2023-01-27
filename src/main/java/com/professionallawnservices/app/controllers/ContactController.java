package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.models.Contact;
import com.professionallawnservices.app.repos.ContactRepo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class ContactController {

    @Autowired
    ContactRepo contactRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/contacts")
    public String contactsView(Model model) {
        model.addAttribute("contacts", contactRepo.findAll());
        return "contacts";
    }

    @GetMapping("/add-contact")
    public String addContactView(Model model) {
        model.addAttribute("contact", new Contact());
        return "add-contact";
    }

    @GetMapping("/update-contact/{id}")
    public String updateContactView(@PathVariable("id") long id, Model model) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", contact);
        return "update-contact";
    }

    @PostMapping("/create-contact")
    public RedirectView createContact(@ModelAttribute Contact contact) {
        contactRepo.save(contact);
        return new RedirectView("/add-contact");
    }

    @PostMapping("/update-contact/{id}")
    public RedirectView updateContact(@PathVariable("id") long id, @ModelAttribute Contact contact,Model model) {
        contact.setContactId(id);
        contactRepo.save(contact);
        return new RedirectView("/update-contact/" + id);
    }

    @GetMapping("/delete-contact/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable("id") long id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        contactRepo.delete(contact);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/search-contacts")
    public ResponseEntity<List<Contact>> getEmployeeByName(@RequestParam(name = "search") String search) {
        List<Contact> contacts = contactRepo.findByContactName(search);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/search-contacts-post")
    public ResponseEntity<List<Contact>> getEmployeeByNamePost(@RequestBody Contact contact) {
        String contactName = contact.getContactName();
        List<Contact> contacts = contactRepo.findByContactName(contactName);
        return ResponseEntity.ok(contacts);
    }

}