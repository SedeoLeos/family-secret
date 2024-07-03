package org.slaega.family_secret.service.impl;

import java.util.Optional;

import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.service.IMagicLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class MagicLinkService implements IMagicLinkService {
    @Autowired
    private MagicLinkRepository magicLinkRepository;

    @Override
    public MagicLinkModel create(String action, UserModel user) {
        MagicLinkModel magicLinkModel = new MagicLinkModel();
        magicLinkModel.setToken(NanoIdUtils.randomNanoId());
        magicLinkModel.setUser(user);
        return this.magicLinkRepository.save(magicLinkModel);
    }

    @Override
    public void deleteById(String id) {
        this.magicLinkRepository.deleteById(id);
    }

    @Override
    public Optional<MagicLinkModel> getById(String id) {
        return this.magicLinkRepository.findById(id);
    }

}
