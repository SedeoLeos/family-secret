package org.slaega.family_secret.passwordless.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slaega.family_secret.passwordless.dto.*;
import org.slaega.family_secret.passwordless.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("sign-in")
    public ResponseEntity<Void> signIn(@RequestBody @Valid SignInRequest singInRequest) {
        authService.signIn(singInRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("sign-in/verify/otp")
    public ResponseEntity<AuthenticationResponse> signINByOTP(@RequestBody @Valid VerifyOTPRequest verifyOTPRequest) {
        AuthenticationResponse auth = authService.loginByOTP(verifyOTPRequest);
        return new ResponseEntity<AuthenticationResponse>(auth, HttpStatus.OK);
    }

    @PostMapping("sign-in/verify/magic-link")
    public ResponseEntity<AuthenticationResponse> signInByMagicLink(@RequestBody @Valid VerifyMagicLinkRequest verifyMagicLinkRequest) {
        AuthenticationResponse auth = authService.loginByMagicLink(verifyMagicLinkRequest);
        return new ResponseEntity<AuthenticationResponse>(auth, HttpStatus.OK);
    }

    @PostMapping("sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest singUpRequest) {
        authService.signUp(singUpRequest);
        return  ResponseEntity.ok().build();

    }
    @PostMapping("sign-up/verify/otp")
    public ResponseEntity<AuthenticationResponse> verifyAccountByOTP(@RequestBody @Valid VerifyOTPRequest verifyOTPRequest) {
        AuthenticationResponse auth = authService.verifyAccountByOTP(verifyOTPRequest);
        return new ResponseEntity<AuthenticationResponse>(auth, HttpStatus.OK);

    }
    @PostMapping("sign-up/verify/magic-link")
    public ResponseEntity<AuthenticationResponse> verifyAccountByMagicLink(@RequestBody @Valid VerifyMagicLinkRequest verifyMagicLinkRequest) {
        AuthenticationResponse auth = authService.verifyAccountByMagicLink(verifyMagicLinkRequest);
        return new ResponseEntity<AuthenticationResponse>(auth, HttpStatus.OK);
    }
    @PostMapping("sign-up/resend-verification")
    public ResponseEntity<Void> resendVerification(@RequestBody @Valid ResendVerificationEmailRequest resendVerificationEmailRequest) {
        authService.resendVerificationEmail(resendVerificationEmailRequest);
        return ResponseEntity.ok().build();

    }

    @PostMapping("refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody @Valid SignUpRequest singUpRequest) {
        AuthenticationResponse authenticationResponse=authService.refresh("singUpRequest");
        return  ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);

    }

    @PostMapping("sign-out")
    public ResponseEntity<AuthenticationResponse> signOut(@RequestBody @Valid SignOut signOut) {
        authService.signOut(signOut);
        return  ResponseEntity.status(HttpStatus.OK).build();


    }
}
