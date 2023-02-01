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

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving contacts.");
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

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the contact with name: " + contactRequest.getName());
        }

        return result;
    }

    public Result getContactById(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = contactRepo.findById(contactRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + contactRequest.getId()));

            result.setData(contact);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the contact with id: " + contactRequest.getId());
        }

        return result;
    }

    public Result updateContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = new Contact(
                    contactRequest.getName(),
                    contactRequest.getPhone(),
                    contactRequest.getEmail()
            );

            contactRepo.save(contact);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the contact with id: " + contactRequest.getId());
        }

        return result;
    }

    public Result deleteContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = contactRepo.findById(contactRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + contactRequest.getId()));
            contactRepo.delete(contact);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the contact with id: " + contactRequest.getId());
        }

        return result;
    }



}
