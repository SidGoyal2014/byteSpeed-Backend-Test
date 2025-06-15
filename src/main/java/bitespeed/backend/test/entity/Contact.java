package bitespeed.backend.test.entity;

import bitespeed.backend.test.enums.LinkPrecedence;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Contact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email(message = "Email cannot be blank or invalid")
    private String email;

    private String phoneNumber;

    private Integer linkedId;

    @NotNull(message = "Link precedence cannot be blank")
    @Enumerated(EnumType.STRING)
    private LinkPrecedence linkPrecedence;

    @NotNull(message = "Created date cannot be blank")
    private LocalDate createdAt;

    @NotNull(message = "Updated date cannot be null")
    private LocalDate updatedAt;

    private LocalDate deletedAt;

    public Contact(String email, String phoneNumber, Integer linkedId, LinkPrecedence linkPrecedence, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.linkedId = linkedId;
        this.linkPrecedence = linkPrecedence;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
