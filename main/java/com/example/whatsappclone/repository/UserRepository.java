package com.example.whatsappclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.whatsappclone.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // find message by sender
    Optional<User> findByUserName(String username);

}
