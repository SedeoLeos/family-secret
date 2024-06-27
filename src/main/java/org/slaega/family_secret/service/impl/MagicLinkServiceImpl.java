package org.slaega.family_secret.service.impl;

import java.util.Optional;

import org.slaega.family_secret.mobel.MagicTokenModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.service.MagicLinkService;
import org.springframework.stereotype.Service;

@Service
public class MagicLinkServiceImpl implements MagicLinkService {
    private MagicLinkRepository magicLinkRepository;

    @Override
    public MagicTokenModel create(MagicTokenModel data) {
        return this.magicLinkRepository.save(data);
    }

    @Override
    public void deleteById(String id) {
        this.magicLinkRepository.deleteById(id);
        
    }

    @Override
    public Optional<MagicTokenModel> getById(String id) {
        return this.magicLinkRepository.findById(id);
    }

}
