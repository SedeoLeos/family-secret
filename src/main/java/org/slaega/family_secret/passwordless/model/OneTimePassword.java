package org.slaega.family_secret.passwordless.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.passwordless.util.Action;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "one-time-password")
@Data
public class OneTimePassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column
    private Date expiresAt;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="auth_id")
    private AuthUser auth;

}
