package org.slaega.family_secret.passwordless.service.impl;

import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.repository.AuthUserRepository;
import org.slaega.family_secret.passwordless.service.IAuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthUserService implements IAuthUserService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public Optional<AuthUser> findById(UUID id) {
        return this.authUserRepository.findById(id);
    }

    @Override
    public AuthUser save(AuthUser authUser) {
        return this.authUserRepository.save(authUser);
    }

    @Override
    public void deleteById(UUID id) {
        this.authUserRepository.deleteById(id);
    }

}
