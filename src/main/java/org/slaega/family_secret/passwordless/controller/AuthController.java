package org.slaega.family_secret.passwordless.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slaega.family_secret.passwordless.dto.*;
import org.slaega.family_secret.passwordless.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @SecurityRequirement(name = "Authorization")
    @PostMapping("refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestHeader Map<String,String>  headers) {
        CreateRefreshRequest refreshRequest = new CreateRefreshRequest();
        refreshRequest.setAuthorization(headers.get("Authorization"));
        AuthenticationResponse authenticationResponse= authService.refresh(refreshRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);

    }

    @SecurityRequirement(name = "Authorization")

    @PostMapping("sign-out")
    @Parameters({
            @Parameter(name = "Authorization", description = "Access Token", required = true, allowEmptyValue = false, in = ParameterIn.HEADER,  example = "Bearer access_token"),
            @Parameter(name = "X-Custom-Header", description = "A Custom Header", required = true, allowEmptyValue = false, in = ParameterIn.HEADER,  example = "my header example")
    })
    public ResponseEntity<AuthenticationResponse> signOut(@RequestBody @Valid SignOut signOut) {
        authService.signOut(signOut);
        return  ResponseEntity.status(HttpStatus.OK).build();


    }
}
