package org.slaega.family_secret.dto.message;

import org.slaega.family_secret.dto.discussion.DiscussionDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageDto {
    String id;
    String content;
    DiscussionDto discussionDto;
    
}
