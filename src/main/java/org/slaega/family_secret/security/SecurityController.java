package org.slaega.family_secret.security;


import org.slaega.family_secret.dto.auth.SignInRequest;
import org.slaega.family_secret.dto.auth.SignUpRequest;
import org.slaega.family_secret.service.impl.AuthService;
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
    @Autowired
    private final AuthService authService;
    @PostMapping("sign-in")
    public void signIn(@RequestBody @Valid  SignInRequest singInRequest) {
    authService.signIn(singInRequest);
    }
    @PostMapping("sing-up")
    public void signUp(@RequestBody @Valid  SignUpRequest singUpRequest) {
    authService.signUp(singUpRequest);
    }

    private void validateOneTimePassword() {
        // TODO: verifier email and send token and magiclink

    }

    private void validateMagicLink() {
        // TODO: verifier email and send token and magiclink

    }

    
}
