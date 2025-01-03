package com.example.whatsappclone.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//this will be the class to manage  all necessary configurations for the connection between the clients and the server using websocket
@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin(origins = "*")
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // Cors configuration

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    // here we are going to configure the stomp endpoint where all clients will
    // connect , this is the chat room url
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // here we are going to configure the message broker , the message broker will
        // be responsible for the communication between the clients and the server
        // the chats will be the endpoint to broadcast the messages to all clients
        // the private will the endpoint to send and receive private messages
        registry.enableSimpleBroker("/topic", "/private", "/group", "/user", "/public");

        // the app will the prefix of messages sent by clients to the server
        registry.setApplicationDestinationPrefixes("/app");

        // here we are going to add the user destination prefix to add can send privates
        // messages
        registry.setUserDestinationPrefix("/user");
    }
}
