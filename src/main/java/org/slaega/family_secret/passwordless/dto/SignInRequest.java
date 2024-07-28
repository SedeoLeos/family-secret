package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInRequest {
    @NotEmpty
    private String email;
    // Getters and Setters
}




