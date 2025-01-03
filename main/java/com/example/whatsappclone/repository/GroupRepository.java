package com.example.whatsappclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.whatsappclone.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);

}
