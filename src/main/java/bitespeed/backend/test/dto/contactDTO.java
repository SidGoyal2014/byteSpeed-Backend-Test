package bitespeed.backend.test.dto;

import bitespeed.backend.test.enums.LinkPrecedence;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class contactDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class IdentifyContactRequestDTO {
        private String email;
        private String phoneNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class IdentifyContactResponseDTO {
        private IdentifyContactResponseSimpleContactDTO contact;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class IdentifyContactResponseSimpleContactDTO {
        private Integer primaryContatctId;
        private List<String> emails;
        private List<String> phoneNumbers;
        private List<Integer> secondaryContactIds;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class CreateContactDTO{
        private String email;
        private String phoneNumber;
        private Integer linkedId;
        private LinkPrecedence linkPrecedence;
    }
}
