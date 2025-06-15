package bitespeed.backend.test.dto;

import bitespeed.backend.test.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import bitespeed.backend.test.enums.LinkPrecedence;

import java.util.List;
import java.util.Set;

public class ContactDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifyContactRequestDTO {
        private String email;
        private String phoneNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifyContactResponseDTO {
        private IdentifyContactResponseSimpleContactDTO contact;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifyContactResponseSimpleContactDTO {
        private Integer primaryContactId;
        private Set<String> emails;
        private Set<String> phoneNumbers;
        private Set<Integer> secondaryContactIds;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateContactDTO{
        private String email;
        private String phoneNumber;
        private Integer linkedId;
        private LinkPrecedence linkPrecedence;
    }
}
