package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.config.JwtConfig;
import org.slaega.family_secret.mobel.MagicLink;
import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.service.IMagicLinkService;
import org.slaega.family_secret.util.JwtUtil;
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
    
    private final MagicLinkRepository magicLinkRepository;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    @Autowired
    public MagicLinkService(MagicLinkRepository magicLinkRepository,JwtConfig jwtConfig){
        this.magicLinkRepository = magicLinkRepository;
        this.jwtConfig =jwtConfig;
        this.jwtUtil = new JwtUtil(jwtConfig.getMagicLinkSecret(), jwtConfig.getMagicLinkExpired());

    }

    @Override
    public String create(String action, User user) {
        deleteAllByUserIdAndAction(action, user);
        MagicLink magicLinkModel = new MagicLink();
        magicLinkModel.setToken(NanoIdUtils.randomNanoId());
        magicLinkModel.setUser(user);
        magicLinkModel.setAction(action);
        magicLinkModel.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        magicLinkModel =  this.magicLinkRepository.save(magicLinkModel);
        Map<String,Object>  extraClaims = new HashMap<>();
        extraClaims.put("userId", magicLinkModel.getUser().getId());
        extraClaims.put("action", magicLinkModel.getAction());
        return jwtUtil.generateToken(extraClaims,magicLinkModel.getToken());
    }

    @Override
    public void deleteById(UUID id) {
        this.magicLinkRepository.deleteById(id);
    }

    @Override
    public Optional<MagicLink> getById(UUID id) {
        return this.magicLinkRepository.findById(id);
    }
    public void deleteAllByUserIdAndAction(String action, User user){
        magicLinkRepository.deleteAllByUserIdAndAction(user, action);
    }
    public MagicLink findByTokenAndAction(String token, String action){
        MagicLink  magicToken = new MagicLink();
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
