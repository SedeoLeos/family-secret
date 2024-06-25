package org.slaega.family_secret.mobel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "one-time-passwprd")
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

}
