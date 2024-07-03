package org.slaega.family_secret.dto.discussion;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDiscussionDto {
    
    private String name;
    @NotNull(message = "Members ID list cannot be null")
    @NotEmpty(message = "Members ID list cannot be empty")
    @Size(min = 1, message = "Members ID list must contain at least one member")
    private List<String> membersId;
}
