package org.slaega.family_secret.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class PasswordLessAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("*************************Slaega********************");
        String username = authentication.getName();
        String credential = (String) authentication.getCredentials();
        boolean valid = false;
        if (isOtp(credential)) {
            // valid = otpService.validateOtp(username, credential);
        } else if (isMagicLink(credential)) {
            // valid = magicLinkService.validateMagicLink(credential);
        }

        if (!valid) {
            throw new BadCredentialsException("Invalid OTP or Magic Link");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(username, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, credential, authorities);
    }

    private boolean isOtp(String credential) {
        // Implémentez votre logique pour déterminer si la credential est OTP
        return credential.matches("\\d{6}"); // Exemple: un OTP de 6 chiffres
    }

    private boolean isMagicLink(String credential) {
        // Implémentez votre logique pour déterminer si la credential est un magic link
        return credential.startsWith("magic-link-"); // Exemple: magic link commence par "magic-link-"
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordLessAuthenticationProvider.class.isAssignableFrom(authentication);
    }

}
