package org.slaega.family_secret.service.impl;

import java.security.CryptoPrimitive;
import java.security.SecureRandom;
import java.util.Optional;

import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.repository.OneTimePasswordRepository;
import org.slaega.family_secret.service.OneTimePasswordService;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Service
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

    private OneTimePasswordRepository oneTimePasswordRepository;

    private OneTimePasswordModel generateOTP(String action, String userId) {
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999) + 100000;
        OneTimePasswordModel oneTimePasswordModel = new OneTimePasswordModel();
        oneTimePasswordModel.setAction(action);
        oneTimePasswordModel.setCode(String.valueOf(code));
        //oneTimePasswordModel.setUser();
        return this.create(oneTimePasswordModel);

    }

    private String generateMagicToken(String action, String userId) {
        String token = NanoIdUtils.randomNanoId();
        return token;
    }

    @Override
    public OneTimePasswordModel create(OneTimePasswordModel oneTimePasswordModel) {
        return this.oneTimePasswordRepository.save(oneTimePasswordModel);
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
