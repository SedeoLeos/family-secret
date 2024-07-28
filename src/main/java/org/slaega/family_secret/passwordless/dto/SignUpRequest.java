package org.slaega.family_secret.passwordless.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

}
