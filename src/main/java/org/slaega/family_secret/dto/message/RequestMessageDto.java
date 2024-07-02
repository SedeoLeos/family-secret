package org.slaega.family_secret.dto.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestMessageDto {
    String content;
    String discussionId;
    String senderId;
}
