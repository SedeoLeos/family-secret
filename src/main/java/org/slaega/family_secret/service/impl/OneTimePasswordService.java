package org.slaega.family_secret.service.impl;

import java.security.CryptoPrimitive;
import java.security.SecureRandom;
import java.util.Optional;

import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.repository.OneTimePasswordRepository;
import org.slaega.family_secret.service.IOneTimePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Service
public class OneTimePasswordService implements IOneTimePasswordService {
    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;

    @Override
    public OneTimePasswordModel create(String action, UserModel user) {
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999) + 100000;
        OneTimePasswordModel oneTimePasswordModel = new OneTimePasswordModel();
        oneTimePasswordModel.setAction(action);
        oneTimePasswordModel.setCode(String.valueOf(code));
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
