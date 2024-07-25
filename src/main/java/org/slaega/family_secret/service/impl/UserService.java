package org.slaega.family_secret.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.repository.UserRepository;
import org.slaega.family_secret.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        this.userRepository.deleteById(id);
    }

}
