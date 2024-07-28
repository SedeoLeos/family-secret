package org.slaega.family_secret.passwordless.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiresAt;
    private long refreshTokenExpiresAt;
}
