package org.slaega.family_secret.dto.auth;

import lombok.Data;

@Data
public class VerifyAccountByMagicLinkRequest {
    private String token;
    // Getters and Setters
}
