package bitespeed.backend.test.services;

import bitespeed.backend.test.dto.ContactDTO;

public interface ContactService {

    public ContactDTO.IdentifyContactResponseDTO identifyContact(ContactDTO.IdentifyContactRequestDTO identifyContactRequestDTO);
}
