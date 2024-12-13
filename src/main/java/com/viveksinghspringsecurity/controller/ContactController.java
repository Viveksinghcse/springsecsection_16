package com.viveksinghspringsecurity.controller;

import com.viveksinghspringsecurity.model.Contact;
import com.viveksinghspringsecurity.repository.ContactRepository;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping("/contact")
   @PreFilter("filterObject.contactName != 'Test'")
    public Contact saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        Contact contact = contacts.getFirst();
        if (null != contact){
            contact.setContactId(getServiceReqNumber());
            contact.setCreateDt(new Date(System.currentTimeMillis()));
            return contactRepository.save(contact);
        } else {

            return null;
        }

    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }
}
