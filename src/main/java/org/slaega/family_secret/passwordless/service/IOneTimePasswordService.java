package org.slaega.family_secret.passwordless.service;


import org.slaega.family_secret.passwordless.dto.VerifyOTPRequest;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.model.OneTimePassword;
import org.slaega.family_secret.passwordless.util.Action;


public interface IOneTimePasswordService {
    OneTimePassword create(Action action, AuthUser authUser);
    AuthUser verifyAccount(VerifyOTPRequest request);
    AuthUser verifyLogin(VerifyOTPRequest request);
}
