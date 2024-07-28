package org.slaega.family_secret.passwordless.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final SecretKey secretKey;
    private final long tokenExpiration;
    private final String keyType;


    private JwtUtil(PrivateKey privateKey, PublicKey publicKey, Long expire) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.secretKey = null;
        this.tokenExpiration = expire;
        this.keyType = "rsa";
    }
    public long getTokenExpiration (){
        return tokenExpiration;
    }

    // Constructeur pour Secret Key
    private JwtUtil(String secretKey, Long expire) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.privateKey = null;
        this.publicKey = null;
        this.tokenExpiration = expire * 1000;
        this.keyType = "secret";
    }

    public static JwtUtil createInstance(JwtUtilParameters params) throws Exception {
        if ("rsa".equalsIgnoreCase(params.getKeyType())) {
            PrivateKey privateKey = RsaKeyUtil.getPrivateKey(params.getPrivateKeyPath());
            PublicKey publicKey = RsaKeyUtil.getPublicKey(params.getPublicKeyPath());
            return new JwtUtil(privateKey, publicKey, params.getTokenExpiration());
        } else {
            return new JwtUtil(params.getSecretKey(), params.getTokenExpiration());
        }
    }



    public String generateToken(String subjet) {
        return generateToken(new HashMap<>(), subjet);
    }

    public String generateToken(Map<String, Object> extraClaims, String subjet) {
        return buildToken(extraClaims, subjet);
    }


    private String buildToken(Map<String, Object> extraClaims, String subject) {
        if ("rsa".equalsIgnoreCase(keyType)) {
            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(privateKey, SignatureAlgorithm.RS256)
                    .compact();
        }
        return Jwts.builder().claims().empty().add(extraClaims).and().subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String original) {
        final String subject = getSubject(token);
        return (subject.equals(original)) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        if ("rsa".equalsIgnoreCase(keyType)) {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build().parseSignedClaims(token)
                    .getPayload();
        }
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }


}
