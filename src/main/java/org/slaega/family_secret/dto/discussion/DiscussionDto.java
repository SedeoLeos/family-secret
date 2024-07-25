package org.slaega.family_secret.dto.discussion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slaega.family_secret.dto.member.MemberDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscussionDto {
    private UUID id;
    private String name;
    private List<MemberDto> members;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
