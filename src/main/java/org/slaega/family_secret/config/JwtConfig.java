package org.slaega.family_secret.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JwtConfig {

    @Value("${jwt.access.secret}")
    private String accessSecret;
    @Value("${jwt.access.expired}")
    private Long accessExpired;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;
    @Value("${jwt.refresh.expired}")
    private Long refreshExpired;
    @Value("${jwt.magiclink.secret}")
    private String magicLinkSecret;
    @Value("${jwt.magiclink.expired}")
    private Long magicLinkExpired;

}
