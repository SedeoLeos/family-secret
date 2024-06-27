package org.slaega.family_secret.mobel;

import java.time.LocalDateTime;
import java.util.List;

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
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    private String phone;
    @Column
    private Gender gender;
    @Column
    private String email;
    @Column
    private LocalDateTime birthday;

    @OneToMany
    private List<MemberModel> members;
}
