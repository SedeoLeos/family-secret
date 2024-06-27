package org.slaega.family_secret.mobel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
@Entity
@Table(name = "one-time-password")
@Data
public class OneTimePasswordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String code;

    @Column
    private String action;

    @Column
    private LocalDateTime expiresAt;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne
    private UserModel user;

}
