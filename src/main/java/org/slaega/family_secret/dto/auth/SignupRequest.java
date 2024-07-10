package org.slaega.family_secret.dto.auth;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    // Getters and Setters
}
