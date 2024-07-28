package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignOut {
    @NotNull
    @NotEmpty
    private String token;
}
