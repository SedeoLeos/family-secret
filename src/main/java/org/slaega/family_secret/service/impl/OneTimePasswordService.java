package org.slaega.family_secret.service.impl;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.OneTimePassword;
import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.repository.OneTimePasswordRepository;
import org.slaega.family_secret.service.IOneTimePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;




@Service
public class OneTimePasswordService implements IOneTimePasswordService {
    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;


    @Override
    public OneTimePassword create(String action, User user) {
        deleteAllByUserIdAndAction(user,action);
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999) + 100000;
        OneTimePassword oneTimePasswordModel = new OneTimePassword();
        oneTimePasswordModel.setAction(action);
        oneTimePasswordModel.setCode(String.valueOf(code));
        return this.oneTimePasswordRepository.save(oneTimePasswordModel);
    }
    public OneTimePassword findByUserIdAndCodeAndAction(User user,String code,String action){
        OneTimePassword example = new OneTimePassword();

        OneTimePassword otp = oneTimePasswordRepository.findOne(Example.of(example)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token"));
         if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token expired");
        }
        return otp;
    }
    public void deleteAllByUserIdAndAction(User user,String action) {
        oneTimePasswordRepository.deleteAllByUserIdAndAction(user, action);
    }

    @Override
    public void deleteById(UUID id) {
        this.oneTimePasswordRepository.deleteById(id);
    }

    @Override
    public Optional<OneTimePassword> getById(UUID id) {
        return this.oneTimePasswordRepository.findById(id);
    }

}
