package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendVerificationEmailRequest {
    @NotEmpty
    @Email
    @NotNull
    private String email;
}
