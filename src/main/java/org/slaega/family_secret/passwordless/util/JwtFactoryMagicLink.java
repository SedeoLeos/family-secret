package org.slaega.family_secret.passwordless.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
@Component
public class JwtFactoryMagicLink {
    private final Map<Action, JwtUtil> jwtUtilMap = new EnumMap<>(Action.class);
    public JwtFactoryMagicLink(
            @Value("${auth.magicLink.accountVerificationJwtSecret}") String accountVerificationSecret,
            @Value("${auth.magicLink.accountVerificationJwtExpiresIn}") long accountVerificationExpiresIn,
            @Value("${auth.magicLink.emailChangeJwtSecret}") String emailChangeSecret,
            @Value("${auth.magicLink.emailChangeJwtExpiresIn}") long emailChangeExpiresIn,
            @Value("${auth.magicLink.loginJwtSecret}") String loginSecret,
            @Value("${auth.magicLink.loginJwtExpiresIn}") long loginExpiresIn

    ) throws Exception {
        jwtUtilMap.put(Action.EMAIL_VERIFICATION, JwtUtil.createInstance(JwtUtilParameters.builder()
                .keyType("secret")
                .secretKey(accountVerificationSecret)
                .tokenExpiration(accountVerificationExpiresIn)
                .build()
        ));
        jwtUtilMap.put(Action.CHANGE_EMAIL,JwtUtil.createInstance(JwtUtilParameters.builder()
                .keyType("secret")
                .secretKey(emailChangeSecret)
                .tokenExpiration(emailChangeExpiresIn)
                .build()
        ));
        jwtUtilMap.put(Action.LOGIN,JwtUtil.createInstance(JwtUtilParameters.builder()
                .keyType("secret")
                .secretKey(loginSecret)
                .tokenExpiration(loginExpiresIn)
                .build()
        ));

    }

    public JwtUtil getJwtUtil(Action action) {
        return jwtUtilMap.get(action);
    }
}
