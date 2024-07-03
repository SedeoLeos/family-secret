package org.slaega.family_secret.dto.member;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemberDto {
    private String id;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
