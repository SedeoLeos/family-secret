package org.slaega.family_secret.service.impl;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.slaega.family_secret.mobel.UserModel;
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
    public OneTimePasswordModel create(String action, UserModel user) {
        deleteAllByUserIdAndAction(user,action);
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999) + 100000;
        OneTimePasswordModel oneTimePasswordModel = new OneTimePasswordModel();
        oneTimePasswordModel.setAction(action);
        oneTimePasswordModel.setCode(String.valueOf(code));
        return this.oneTimePasswordRepository.save(oneTimePasswordModel);
    }
    public OneTimePasswordModel findByUserIdAndCodeAndAction(UserModel user,String code,String action){
        OneTimePasswordModel example = new OneTimePasswordModel();

        OneTimePasswordModel otp = oneTimePasswordRepository.findOne(Example.of(example)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token"));
         if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token expired");
        }
        return otp;
    }
    public void deleteAllByUserIdAndAction(UserModel user,String action) {
        oneTimePasswordRepository.deleteAllByUserIdAndAction(user, action);
    }

    @Override
    public void deleteById(String id) {
        this.oneTimePasswordRepository.deleteById(id);
    }

    @Override
    public Optional<OneTimePasswordModel> getById(String id) {
        return this.oneTimePasswordRepository.findById(id);
    }

}
