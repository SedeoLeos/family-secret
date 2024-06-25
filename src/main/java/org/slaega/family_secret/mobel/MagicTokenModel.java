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
@Table(name = "magic-link")
public class MagicTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String token;

    @Column
    private String action;
    
    @Column
    private LocalDateTime expiresAt;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

}