package org.slaega.family_secret.passwordless.config;

import org.slaega.family_secret.passwordless.util.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
@Component
public class OneTimePasswordFactoryExpire {
    private final Map<Action, Long> expiresInMap = new EnumMap<>(Action.class);
    public OneTimePasswordFactoryExpire(

            @Value("${auth.otp.accountVerificationJwtExpiresIn}") long accountVerificationExpiresIn,

            @Value("${auth.otp.emailChangeJwtExpiresIn}") long emailChangeExpiresIn,

            @Value("${auth.otp.loginJwtExpiresIn}") long loginExpiresIn

    ) throws Exception {
        expiresInMap.put(Action.EMAIL_VERIFICATION,accountVerificationExpiresIn);
        expiresInMap.put(Action.CHANGE_EMAIL,emailChangeExpiresIn);
        expiresInMap.put(Action.LOGIN,loginExpiresIn);

    }

    public long expiresIn(Action action) {
        return expiresInMap.get(action);
    }
}
