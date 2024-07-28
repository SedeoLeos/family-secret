package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyMagicLinkRequest {
    @NotEmpty
    private String token;
    // Getters and Setters
}
