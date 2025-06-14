package bitespeed.backend.test.services.impl;

import bitespeed.backend.test.dto.contactDTO.*;
import bitespeed.backend.test.entity.Contact;
import bitespeed.backend.test.repository.IContactRepository;
import bitespeed.backend.test.repository.IContactRepository;
import bitespeed.backend.test.services.ContactService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ContactServiceImpl implements ContactService {

    private IContactRepository contactRepository;

    public ContactServiceImpl(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void identifyContact(IdentifyContactRequestDTO identifyContactRequestDTO) {

        // check if phone or email exists
        String phoneNumber = identifyContactRequestDTO.getPhoneNumber();
        String email = identifyContactRequestDTO.getEmail();

        // if yes

        // if no
    }

    private void createContact(final CreateContactDTO createContactDTO){

        Contact contact = new Contact(
                1,
                createContactDTO.getEmail(),
                createContactDTO.getPhoneNumber(),
                createContactDTO.getLinkedId(),
                createContactDTO.getLinkPrecedence(),
                LocalDate.now(),
                LocalDate.now(),
                null
        );

        createContactDTO.builder()
                .email(createContactDTO.getEmail())
                .phoneNumber(createContactDTO.getPhoneNumber())
                .linkedId(createContactDTO.getLinkedId())
                .linkPrecedence(createContactDTO.getLinkPrecedence())
                .build();
        // create contact logic
        // save to database
        // return response
    }
}
