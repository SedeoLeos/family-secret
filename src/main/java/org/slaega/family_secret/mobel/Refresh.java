package org.slaega.family_secret.mobel;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "refresh")
@Data
public class Refresh {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column
    private String token;

    @ManyToOne
    private User user;
   
    @Column
    private LocalDateTime expiresAt;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
}
