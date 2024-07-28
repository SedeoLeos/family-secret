package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerifyOTPRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String code;

   
}
