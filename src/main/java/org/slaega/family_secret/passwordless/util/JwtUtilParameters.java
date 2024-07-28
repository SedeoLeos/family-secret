package org.slaega.family_secret.passwordless.util;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtUtilParameters {
    private String keyType;
    private String privateKeyPath;
    private String publicKeyPath;
    private String secretKey;
    private long tokenExpiration;
}
