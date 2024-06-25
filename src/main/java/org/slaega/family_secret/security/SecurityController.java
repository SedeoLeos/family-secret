package org.slaega.family_secret.security;

import org.slaega.family_secret.dto.LoginWithEmailOrPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {
    @PostMapping
    public String login(@RequestBody @Valid  LoginWithEmailOrPhone loginWithEmailOrPhone) {
       return loginWithEmailOrPhone.getEmail();
    }

    private void validateOneTimePassword() {
        // TODO: verifier email and send token and magiclink

    }

    private void validateMagicLink() {
        // TODO: verifier email and send token and magiclink

    }
}
