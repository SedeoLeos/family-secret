package org.slaega.family_secret.mobel;

import java.util.List;

import org.slaega.family_secret.enums.DiscussionAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "members")
@Data
public class MemberModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private DiscussionAccess access;
    @OneToMany
    private List<MessageMobel> messages;
    @ManyToOne
    private DiscussionModel discussion;
}
