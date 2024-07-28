package org.slaega.family_secret.passwordless.service;


import org.slaega.family_secret.passwordless.dto.VerifyMagicLinkRequest;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.util.Action;



public interface IMagicLinkService {
    String create(Action action, AuthUser user);
    AuthUser verifyAccount(VerifyMagicLinkRequest request);
    AuthUser verifyLogin(VerifyMagicLinkRequest request);


}