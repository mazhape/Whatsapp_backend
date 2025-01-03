package com.example.whatsappclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.whatsappclone.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderId(Long senderId);

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Query("SELECT m FROM Message m WHERE m.senderId = ?1 OR m.receiverId = ?1")
    List<Message> findAllMessagesByUser(Long userId);

}
