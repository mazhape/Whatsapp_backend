package com.example.whatsappclone.service;

import org.springframework.stereotype.Service;

import com.example.whatsappclone.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.whatsappclone.models.Message;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    // adding message to the database
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    // finding messages by senderId
    public List<Message> findMessagesBySenderId(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    // find messages by receiverId
    public List<Message> findMessagesByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    public List<Message> findMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public List<Message> findAllMessagesByUser(Long userId) {
        return messageRepository.findAllMessagesByUser(userId);
    }
}
