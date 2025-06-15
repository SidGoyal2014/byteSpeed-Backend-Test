package bitespeed.backend.test.services.impl;

import bitespeed.backend.test.dto.ContactDTO.*;
import bitespeed.backend.test.entity.Contact;
import bitespeed.backend.test.repository.IContactRepository;
import bitespeed.backend.test.services.ContactService;
import org.springframework.stereotype.Service;
import bitespeed.backend.test.enums.LinkPrecedence;
import bitespeed.backend.test.dto.ContactDTO.IdentifyContactResponseDTO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private IContactRepository contactRepository;

    public ContactServiceImpl(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public IdentifyContactResponseDTO identifyContact(IdentifyContactRequestDTO identifyContactRequestDTO) {

        // System.out.println("Identify Contact Request: " + identifyContactRequestDTO);
        // System.out.println("BABABABAB");

        // check if phone or email exists
        String phoneNumber = identifyContactRequestDTO.getPhoneNumber();
        String email = identifyContactRequestDTO.getEmail();

        // get all the matching contacts by phone or email
        List<Contact> contactsByEmail = contactRepository.findAllByEmail(email);
        List<Contact> contactsByPhone = contactRepository.findAllByPhoneNumber(phoneNumber);

        // Create a list of all the contacts
        List<Contact> allContacts = new ArrayList<>();
        allContacts.addAll(contactsByEmail);
        allContacts.addAll(contactsByPhone);

        for(Contact contact : allContacts){
            System.out.println("Contact: " + contact);
        }

        // Get the primary Contact
        Contact primaryContact = getPrimaryContact(allContacts);
        System.out.println("Primary Contact: " + primaryContact);

        // Select the contact with the oldest createdOn date as primary
        Contact oldestContact = allContacts.stream()
                        .min((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt()))
                        .orElse(primaryContact);

        System.out.println("Oldest Contact: " + oldestContact);

        // Create Response Contact
        IdentifyContactResponseSimpleContactDTO responseContact = null;

        Set<Integer> secondaryContacts = allContacts.stream()
                .filter(contact -> (contact.getLinkPrecedence() == LinkPrecedence.secondary)
                                    &&
                                    (contact.getId() != oldestContact.getId()))
                .map(Contact::getId)
                .collect(Collectors.toSet());

        if((contactsByEmail == null || contactsByEmail.isEmpty()) && (contactsByPhone == null || contactsByPhone.isEmpty())){
            // create a new Contact
            CreateContactDTO createContactDTO = CreateContactDTO.builder()
                                                    .email(email)
                                                    .phoneNumber(phoneNumber)
                                                    .linkPrecedence(LinkPrecedence.primary)
                                                    .linkedId(null)
                                                    .build();

            Contact newContact = createContact(createContactDTO);

            // Return response
            responseContact = IdentifyContactResponseSimpleContactDTO.builder()
                    .primaryContatctId(newContact.getLinkedId())
                    .emails(Set.of(createContactDTO.getEmail()))
                    .phoneNumbers(Set.of(createContactDTO.getPhoneNumber()))
                    .secondaryContactIds(new HashSet<>())
                    .build();
        }
        else if(contactsByEmail != null && !contactsByEmail.isEmpty() && contactsByPhone != null && !contactsByPhone.isEmpty()){

            // Return response
            responseContact = IdentifyContactResponseSimpleContactDTO.builder()
                    .primaryContatctId(oldestContact.getId())
                    .emails(allContacts.stream().map(Contact::getEmail).collect(Collectors.toSet()))
                    .phoneNumbers(allContacts.stream().map(Contact::getPhoneNumber).collect(Collectors.toSet()))
                    .secondaryContactIds(secondaryContacts)
                    .build();
        } else{
            // create Contact
            CreateContactDTO newContact = CreateContactDTO.builder()
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .linkPrecedence(LinkPrecedence.secondary)
                    .linkedId(oldestContact.getId())
                    .build();

            Contact addedContact = createContact(newContact);

            allContacts.add(addedContact);

            // Return response
            responseContact = IdentifyContactResponseSimpleContactDTO.builder()
                    .primaryContatctId(oldestContact.getId())
                    .emails(allContacts.stream().map(Contact::getEmail).collect(Collectors.toSet()))
                    .phoneNumbers(allContacts.stream().map(Contact::getPhoneNumber).collect(Collectors.toSet()))
                    .secondaryContactIds(secondaryContacts)
                    .build();
        }

        return IdentifyContactResponseDTO.builder()
                .contact(responseContact)
                .build();

    }

    private Contact getPrimaryContact(List<Contact> contacts){
        return contacts.stream()
                .filter(contact -> contact.getLinkPrecedence() == LinkPrecedence.primary)
                .findFirst()
                .orElse(null);
    }

    private Contact createContact(final CreateContactDTO createContactDTO){

        Contact contact = new Contact(
                createContactDTO.getEmail(),
                createContactDTO.getPhoneNumber(),
                createContactDTO.getLinkedId(),
                createContactDTO.getLinkPrecedence(),
                LocalDate.now(),
                LocalDate.now(),
                null
        );

        contactRepository.save(contact);

        return contact;
    }
}
