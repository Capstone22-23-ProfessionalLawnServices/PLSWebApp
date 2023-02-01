package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.repos.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.professionallawnservices.app.models.*;

@Service
public class ContactService {

    @Autowired
    ContactRepo contactRepo;

    public Result getAllContacts() {

        Result result = new Result();

        try{

            result.setData(contactRepo.findAll());

            result.complete = true;
        }
        catch (Exception e) {
            result.complete = false;
            result.errorMessage = "There was an issue retrieving contacts.";
        }

        return result;
    }

    public Result createContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = new Contact(
                    contactRequest.getName(),
                    contactRequest.getPhone(),
                    contactRequest.getEmail()
            );

            contactRepo.save(contact);

            result.complete = true;

        }
        catch (Exception e) {
            result.complete = false;
            result.errorMessage = "There was an issue saving the contact with name: " + contactRequest.getName();
        }

        return result;
    }
}
