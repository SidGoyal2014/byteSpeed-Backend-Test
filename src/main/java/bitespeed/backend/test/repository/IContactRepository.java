package bitespeed.backend.test.repository;

import bitespeed.backend.test.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByEmail(String email);
    List<Contact> findByPhoneNumber(String phoneNumber);

    // Custom query methods can be defined here if needed
    // For example:
    // List<Contact> findByEmail(String email);
    // List<Contact> findByPhoneNumber(String phoneNumber);
    // List<Contact> findByLinkedId(int linkedId);
    // List<Contact> findByLinkPrecedence(LinkPrecedence linkPrecedence);
}
