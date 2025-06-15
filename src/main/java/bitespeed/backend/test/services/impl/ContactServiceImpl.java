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
    public IdentifyContactResponseDTO identifyContact(IdentifyContactRequestDTO request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        // Find all contacts matching either email or phone
        List<Contact> contactsByEmail = email != null ? contactRepository.findAllByEmail(email) : new ArrayList<>();
        List<Contact> contactsByPhone = phoneNumber != null ? contactRepository.findAllByPhoneNumber(phoneNumber) : new ArrayList<>();

        // Combine and deduplicate contacts
        Set<Contact> allContactsSet = new HashSet<>();
        allContactsSet.addAll(contactsByEmail);
        allContactsSet.addAll(contactsByPhone);
        List<Contact> allContacts = new ArrayList<>(allContactsSet);

        Contact primaryContact;

        if (allContacts.isEmpty()) {
            // Scenario 1: No existing contacts - create new primary contact
            primaryContact = createNewPrimaryContact(email, phoneNumber);
            allContacts.add(primaryContact);
        } else {
            // Find the oldest contact to be the primary
            Contact oldestContact = allContacts.stream()
                    .min(Comparator.comparing(Contact::getCreatedAt))
                    .orElse(allContacts.get(0));

            // Check if we need to link contacts or create a new secondary
            boolean emailExists = contactsByEmail.stream()
                    .anyMatch(c -> Objects.equals(c.getEmail(), email));
            boolean phoneExists = contactsByPhone.stream()
                    .anyMatch(c -> Objects.equals(c.getPhoneNumber(), phoneNumber));

            if (!emailExists || !phoneExists) {
                // Scenario 2: New email or phone - create secondary contact
                if ((email != null && !emailExists) || (phoneNumber != null && !phoneExists)) {
                    Contact newSecondary = createNewSecondaryContact(email, phoneNumber, oldestContact.getId());
                    allContacts.add(newSecondary);
                }
            }

            // Scenario 3: Link existing separate contacts
            linkExistingContacts(allContacts, oldestContact);
            primaryContact = oldestContact;
        }

        // Build response
        return buildResponse(allContacts, primaryContact);
    }

    private Contact createNewPrimaryContact(String email, String phoneNumber) {
        Contact contact = Contact.builder()
                .email(email)
                .phoneNumber(phoneNumber)
                .linkedId(null)
                .linkPrecedence(LinkPrecedence.primary)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
        return contactRepository.save(contact);
    }

    private Contact createNewSecondaryContact(String email, String phoneNumber, Integer primaryId) {
        Contact contact = Contact.builder()
                .email(email)
                .phoneNumber(phoneNumber)
                .linkedId(primaryId)
                .linkPrecedence(LinkPrecedence.secondary)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();
        return contactRepository.save(contact);
    }

    private void linkExistingContacts(List<Contact> allContacts, Contact primaryContact) {
        for (Contact contact : allContacts) {
            if (contact.getId() != primaryContact.getId()) {
                if (contact.getLinkPrecedence() == LinkPrecedence.primary) {
                    // Convert other primary contacts to secondary
                    contact.setLinkPrecedence(LinkPrecedence.secondary);
                    contact.setLinkedId(primaryContact.getId());
                    contact.setUpdatedAt(LocalDate.now());
                    contactRepository.save(contact);
                } else if (!Objects.equals(contact.getLinkedId(), primaryContact.getId())) {
                    // Update secondary contacts to point to the correct primary
                    contact.setLinkedId(primaryContact.getId());
                    contact.setUpdatedAt(LocalDate.now());
                    contactRepository.save(contact);
                }
            }
        }
    }

    private IdentifyContactResponseDTO buildResponse(List<Contact> allContacts, Contact primaryContact) {
        // Get all linked contacts (including those linked to this primary)
        List<Contact> allLinkedContacts = contactRepository.findAllByLinkedId(primaryContact.getId());
        allLinkedContacts.addAll(allContacts);
        allLinkedContacts = allLinkedContacts.stream().distinct().collect(Collectors.toList());

        Set<String> emails = allLinkedContacts.stream()
                .map(Contact::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<String> phoneNumbers = allLinkedContacts.stream()
                .map(Contact::getPhoneNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Integer> secondaryContactIds = allLinkedContacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.secondary)
                .map(Contact::getId)
                .collect(Collectors.toSet());

        IdentifyContactResponseSimpleContactDTO responseContact = IdentifyContactResponseSimpleContactDTO.builder()
                .primaryContactId(primaryContact.getId()) // Fixed typo
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .secondaryContactIds(secondaryContactIds)
                .build();

        return IdentifyContactResponseDTO.builder()
                .contact(responseContact)
                .build();
    }
}
