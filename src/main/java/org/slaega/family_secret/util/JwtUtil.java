package org.slaega.family_secret.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private final String secretKey;
    private final long tokenExpirationMillis;

    public JwtUtil(String secret,Long expire) {
        secretKey = secret;
        tokenExpirationMillis = expire;
    }

    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String subjet) {
        return generateToken(new HashMap<>(), subjet);
    }

    public String generateToken(Map<String, Object> extraClaims, String subjet) {
        return buildToken(extraClaims, subjet);
    }

    public long getExpirationTime() {
        return tokenExpirationMillis;
    }

    private String buildToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder().claims().empty().add(extraClaims).and().subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationMillis)).signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, String original) {
        final String subject = getSubject(token);
        return (subject.equals(original)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token ).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
