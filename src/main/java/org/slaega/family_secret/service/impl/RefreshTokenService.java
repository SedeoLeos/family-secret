package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.Refresh;
import org.slaega.family_secret.mobel.User;
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
    public String create(User user) {
        Refresh refresh = new Refresh();
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
    public void deleteById(UUID id) {
       refreshRepository.deleteById(id);
    }

    @Override
    public Optional<Refresh> getById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }
    
}
