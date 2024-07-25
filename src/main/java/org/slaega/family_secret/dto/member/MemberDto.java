package org.slaega.family_secret.dto.member;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class MemberDto {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
