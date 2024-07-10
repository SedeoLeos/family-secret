package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.RefreshModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.RefreshRepository;
import org.slaega.family_secret.service.IRefreshTokenService;
import org.slaega.family_secret.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Service
public class RefreshTokenService implements IRefreshTokenService {
    @Value("")
    String secret;
    @Value("")
    Long expired;

    private RefreshRepository refreshRepository;
    private  JwtUtil jwtUtil;

    @Override
    public String create(UserModel user) {
        RefreshModel refresh = new RefreshModel();
        refresh.setToken(NanoIdUtils.randomNanoId());
        refresh.setUser(user);
        refresh.setExpiresAt(LocalDateTime.now().plusDays(30));
        refresh = refreshRepository.save(refresh);
         Map<String,Object>  extraClaims = new HashMap<>();
        extraClaims.put("userId", refresh.getUser().getId());
        extraClaims.put("refreshId", refresh.getId());
        return jwtUtil.generateToken(extraClaims,refresh.getToken());
    }

    @Override
    public void deleteById(String id) {
       refreshRepository.deleteById(id);
    }

    @Override
    public Optional<RefreshModel> getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
    
}
