package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.service.IMagicLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        magicLinkModel.setAction(action);
        magicLinkModel.setCreatedAt(LocalDateTime.now().plusMinutes(15));
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
    public void deleteAllByUserIdAndAction(String action, UserModel user){
        MagicLinkModel magicLinkModel = new MagicLinkModel();
        magicLinkModel.setAction(action);
        magicLinkModel.setUser(user);
        List<MagicLinkModel> list = new ArrayList<>();
        list.add(magicLinkModel);
        
        this.magicLinkRepository.deleteAllInBatch(list);
    }
    public MagicLinkModel findByTokenAndAction(String token, String action){
        MagicLinkModel  magicToken = new MagicLinkModel();
        magicToken.setToken(token);
        magicToken.setAction(action);
          magicToken = magicLinkRepository.findOne(Example.of(magicToken))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        if (magicToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        return magicToken;
    }

}
