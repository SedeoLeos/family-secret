package org.slaega.family_secret.mobel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slaega.family_secret.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
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
    private List<Member> members;
}
