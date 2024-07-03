package org.slaega.family_secret.dto.auth;

import lombok.Data;

@Data
public class VerifyAccountByOtpRequest {
    private String email;
    private String code;
   
}
