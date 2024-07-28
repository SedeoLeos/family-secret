package org.slaega.family_secret.passwordless.config;

import lombok.Data;
import org.slaega.family_secret.passwordless.util.Action;
import org.slaega.family_secret.passwordless.util.JwtUtil;
import org.slaega.family_secret.passwordless.util.JwtUtilParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
@Data
public class JwtConfig {
    private final Map<Action, Long> expiresInMap = new EnumMap<>(Action.class);
    private final Map<Action, JwtUtil> jwtUtilMap = new EnumMap<>(Action.class);


    public JwtConfig(
            @Value("${auth.accessSecret}")
            String accessSecret,
            @Value("${auth.accessPrivateKey}")
            String accessPrivatePath,
            @Value("${auth.accessPublicKey}")
            String accessPublicPath,
            @Value("${auth.accessKeyType}")
            String accessKeyType,
            @Value("${auth.accessExpiresIn}")
            Long accessExpiresIn,
            @Value("${auth.refreshSecret}")
            String refreshSecret,
            @Value("${auth.refreshPrivateKey}")
            String refreshPrivatePath,
            @Value("${auth.refreshPublicKey}")
            String refreshPublicPath,
            @Value("${auth.refreshKeyType}")
            String refreshKeyType,
            @Value("${auth.refreshExpiresIN}")
            Long refreshExpiresIn
    ) throws Exception {
        expiresInMap.put(Action.REFRESH, refreshExpiresIn);
        expiresInMap.put(Action.ACCESS, accessExpiresIn);

        jwtUtilMap.put(Action.ACCESS, JwtUtil.createInstance(JwtUtilParameters
                .builder()
                .secretKey(accessSecret)
                .keyType(accessKeyType)
                .privateKeyPath(accessPrivatePath)
                .publicKeyPath(accessPublicPath)
                .tokenExpiration(accessExpiresIn)
                .build()));
        jwtUtilMap.put(Action.REFRESH, JwtUtil.createInstance(JwtUtilParameters.builder()
                .secretKey(refreshSecret)
                .keyType(refreshKeyType)
                .privateKeyPath(refreshPrivatePath)
                .publicKeyPath(refreshPublicPath)
                .tokenExpiration(refreshExpiresIn).build()));


    }

    public long getExpiresIn(Action action) {
        return expiresInMap.get(action);
    }

    public JwtUtil jwtUtil(Action action) {
        return jwtUtilMap.get(action);
    }


}
