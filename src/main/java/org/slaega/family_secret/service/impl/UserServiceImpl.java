package org.slaega.family_secret.service.impl;

import java.util.Optional;

import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.UserRepository;
import org.slaega.family_secret.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public Optional<UserModel> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public UserModel save(UserModel user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        this.userRepository.deleteById(id);
    }

}
