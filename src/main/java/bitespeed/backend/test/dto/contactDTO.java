package bitespeed.backend.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class contactDTO {

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
}
