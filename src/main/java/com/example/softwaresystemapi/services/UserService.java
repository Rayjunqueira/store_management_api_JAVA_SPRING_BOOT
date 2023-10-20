package com.example.softwaresystemapi.services;

import com.example.softwaresystemapi.models.UserModel;
import com.example.softwaresystemapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public void delete (UserModel userModel) {
        userRepository.delete(userModel);
    }
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
