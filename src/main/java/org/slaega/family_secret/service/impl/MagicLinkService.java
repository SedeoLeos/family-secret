package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.MagicLinkRepository;
import org.slaega.family_secret.service.IMagicLinkService;
import org.slaega.family_secret.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("")
    String secret;
    @Value("")
    Long expired;
    private final MagicLinkRepository magicLinkRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    public MagicLinkService(MagicLinkRepository magicLinkRepository){
        this.magicLinkRepository = magicLinkRepository;
        this.jwtUtil = new JwtUtil(secret, expired);

    }

    @Override
    public String create(String action, UserModel user) {
        deleteAllByUserIdAndAction(action, user);
        MagicLinkModel magicLinkModel = new MagicLinkModel();
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
    public void deleteById(String id) {
        this.magicLinkRepository.deleteById(id);
    }

    @Override
    public Optional<MagicLinkModel> getById(String id) {
        return this.magicLinkRepository.findById(id);
    }
    public void deleteAllByUserIdAndAction(String action, UserModel user){
        magicLinkRepository.deleteAllByUserIdAndAction(user, action);
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
