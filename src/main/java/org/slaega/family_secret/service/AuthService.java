package org.slaega.family_secret.service;

import java.security.CryptoPrimitive;
import java.security.SecureRandom;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class AuthService {
    
    private void generateOTP(String action,String userId) {
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999)+100000;
       
    }
    private void generateMagicToken(String action,String userId){
        String token = NanoIdUtils.randomNanoId();
    }
}
