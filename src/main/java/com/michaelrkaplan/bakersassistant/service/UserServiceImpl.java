package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.dto.UserRequest;
import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRequest userRequest) {
        // Validate userRequest, check if email is unique, etc.

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        // Create a new user entity
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(encodedPassword);

        // Save the user to the database
        userRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        // Check if the user with the provided email exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Use the password encoder to check if the provided password matches the stored hashed password
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found
    }}

