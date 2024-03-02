package com.tony.nftLogin.service;

import com.tony.nftLogin.Message;
import com.tony.nftLogin.model.User;
import com.tony.nftLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;


    public Message.LoginResponse authenticate(Message.LoginRequest request) throws Exception {
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User password is " + user.getPassword());
            System.out.println("request password is " + request.getPassword());
            if (request.getPassword().equals(user.getPassword()) ) {

                String token = generateToken(user);

                // Use Protocol Buffers builder to build a response
                Message.LoginResponse response = Message.LoginResponse.newBuilder()
                        .setUserId(user.getUserId())
                        .setToken(token)
                        .build();

                return response;
            }
        }
        throw new Exception("Authentication failed");
    }

    private String generateToken(User user) {
        // Placeholder implementation
        return UUID.randomUUID().toString();
    }
}
