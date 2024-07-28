package org.slaega.family_secret.passwordless.util;

import java.util.UUID;

public record JwtPayload(String token,
                         UUID authId, long expiresIn) {

}
