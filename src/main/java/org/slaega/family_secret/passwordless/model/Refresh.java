package org.slaega.family_secret.passwordless.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.slaega.family_secret.mobel.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "refresh")
@Data
public class Refresh {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column
    private String token;

    @ManyToOne
    private AuthUser auth;
   
    @Column
    private Date expiresAt;

    @Column
    @CreationTimestamp
    private Date createdAt;
}
