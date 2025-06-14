package bitespeed.backend.test.services;

import bitespeed.backend.test.dto.contactDTO;

public interface ContactService {

    public contactDTO.IdentifyContactResponseDTO identifyContact(contactDTO.IdentifyContactRequestDTO identifyContactRequestDTO);
}
