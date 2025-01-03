package com.example.whatsappclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whatsappclone.repository.UserRepository;
import com.example.whatsappclone.models.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // add User
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // find User by Id
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // find all Users
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }
}
