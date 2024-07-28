package org.slaega.family_secret.passwordless.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaKeyUtil {

    public static PrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        String privateKeyPem = readKey(privateKeyPath);
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String publicKeyPath) throws Exception {
        String publicKeyPem = readKey(publicKeyPath);
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private static String readKey(String keyPath) throws IOException {
        Resource resource = new ClassPathResource(keyPath);
        byte[] keyBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String key = new String(keyBytes, StandardCharsets.UTF_8);
        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        return key;
    }
}

