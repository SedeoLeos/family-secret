package org.slaega.family_secret.dto.message;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestMessageDto {
    String content;
    UUID discussionId;
    String senderId;
}
