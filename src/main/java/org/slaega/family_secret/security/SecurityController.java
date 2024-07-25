package org.slaega.family_secret.security;

import org.slaega.family_secret.dto.auth.SignInRequest;
import org.slaega.family_secret.dto.auth.SignUpRequest;
import org.slaega.family_secret.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {

    @Autowired
    private final AuthService authService;

    @PostMapping("sign-in")
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInRequest singInRequest) {
        authService.signIn(singInRequest);
        return new ResponseEntity<String>("Good", HttpStatus.CREATED);
    }

    @PostMapping("sign-up")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpRequest singUpRequest) {
        authService.signUp(singUpRequest);
        return new ResponseEntity<String>("Good signUp ", HttpStatus.CREATED);

    }

    private void validateOneTimePassword() {
        // TODO: verifier email and send token and magiclink

    }

    private void validateMagicLink() {
        // TODO: verifier email and send token and magiclink

    }

}
