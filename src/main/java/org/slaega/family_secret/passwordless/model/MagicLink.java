package org.slaega.family_secret.passwordless.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.passwordless.util.Action;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "magic-link")
@Data
public class MagicLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String token;

    @Column
    @Enumerated(EnumType.STRING)
    private Action action;

    @ManyToOne
    @JoinColumn(name= "auth_id")
    private AuthUser auth;

    @Column
    private Date expiresAt;

    @Column
    @CreationTimestamp
    private Date createdAt;

}
