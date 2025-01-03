package com.example.whatsappclone.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.http.ResponseEntity;

import com.example.whatsappclone.repository.MessageRepository;
import com.example.whatsappclone.service.UserService;
import com.example.whatsappclone.models.Message;
import com.example.whatsappclone.models.User;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final UserService userService;

    @MessageMapping("/send") // Client sends messages here
    public void sendMessage(Message message) {
        messageRepository.save(message); // Save message to database
        messagingTemplate.convertAndSend("/topic/chats", message); // Broadcast message to all clients

        // Send message to the receiver or group
        if (message.getIsGroupMessage()) {
            messagingTemplate.convertAndSend("/topic/group/" + message.getGroupId(), message);
        } else {
            messagingTemplate.convertAndSend("/topic/user/" + message.getReceiverId(), message);
        }
    }

    // adding a new message
    @PostMapping("/message/send")
    public ResponseEntity<?> sendMessageViaRest(@RequestBody Message message) {
        messagingTemplate.convertAndSend("/topic/chats", message);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> addUserViaRest(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));

    }

    // get all users
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get message by receiverId and senderId
    @GetMapping("/message/{receiverId}/{senderId}")
    public ResponseEntity<?> getMessageByReceiverIdAndSenderId(@PathVariable Long receiverId,
            @PathVariable Long senderId) {
        try {
            return new ResponseEntity<>(messageRepository.findBySenderIdAndReceiverId(senderId, receiverId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}