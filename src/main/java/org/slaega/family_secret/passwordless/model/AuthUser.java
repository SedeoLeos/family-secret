package org.slaega.family_secret.passwordless.model;

import jakarta.persistence.*;
import lombok.Data;
import org.slaega.family_secret.enums.Gender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "auth_user")
@Data
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String phone;
    @Column(nullable=true)
    private Gender gender;
    @Column
    private String email;
    @Column(nullable=true)
    private String role;
    @Column(nullable=true)
    private LocalDateTime birthday;
    @Column(nullable=true)
    private Boolean verified;




    @OneToMany
    private List<MagicLink> magicLinks;
    @OneToMany
    private List<OneTimePassword> oneTimePasswords;

}
