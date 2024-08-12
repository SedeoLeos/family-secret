package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateRefreshRequest {
    @NotEmpty
    private  String Authorization;
}
