package bitespeed.backend.test.entity;

import bitespeed.backend.test.enums.LinkPrecedence;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Contact")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private int id;

    @Email(message = "Email cannot be blank or invalid")
    private String email;

    private String phoneNumber;

    private int linkedId;

    @NotBlank
    private LinkPrecedence linkPrecedence;

    @NotBlank
    private LocalDate createdAt;

    @NotBlank
    private LocalDate updatedAt;

    private LocalDate deletedAt;
}
