package org.slaega.family_secret.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class SecurityController {
    //@Autowired
    //private AuthenticationManager authenticationManager;
    
    private void login(){
        //TODO: verifier email and send token and magiclink
    }
    private void validateOneTimePassword(){
        //TODO: verifier email and send token and magiclink

    }
    private  void validateMagicLink(){
        //TODO: verifier email and send token and magiclink
        
    }
}
