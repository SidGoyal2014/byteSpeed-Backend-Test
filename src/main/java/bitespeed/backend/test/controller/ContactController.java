package bitespeed.backend.test.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bitespeed.backend.test.services.ContactService;
import bitespeed.backend.test.dto.ContactDTO.IdentifyContactRequestDTO;

@RestController
@RequestMapping("/")
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/identify")
    public ResponseEntity<?> indetifyContact(@RequestBody IdentifyContactRequestDTO request) {
        return ResponseEntity.ok().body(contactService.identifyContact(request));
    }
}


