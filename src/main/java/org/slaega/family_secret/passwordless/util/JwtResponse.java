package org.slaega.family_secret.passwordless.util;

public record JwtResponse(String jwt,long expiresIn) {
}
