package bitespeed.backend.test.repository;

import bitespeed.backend.test.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    // Custom query methods can be defined here if needed
    // For example:
    // List<Contact> findByEmail(String email);
    // List<Contact> findByPhoneNumber(String phoneNumber);
    // List<Contact> findByLinkedId(int linkedId);
    // List<Contact> findByLinkPrecedence(LinkPrecedence linkPrecedence);
}
