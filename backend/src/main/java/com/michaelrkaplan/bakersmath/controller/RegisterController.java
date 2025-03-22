package com.michaelrkaplan.bakersmath.controller;

import com.michaelrkaplan.bakersmath.dto.RegistrationForm;
import com.michaelrkaplan.bakersmath.repository.UserRepository;
import com.michaelrkaplan.bakersmath.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from React frontend
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationForm registrationForm, Errors errors) {
        String username = registrationForm.getUsername();
        String email = registrationForm.getEmail();
        String password = registrationForm.getPassword();

        // Convert email to lowercase for case-insensitive comparison
        String normalizedEmail = email.toLowerCase();

        // Validate email and username uniqueness
        validateEmailUniqueness(errors, normalizedEmail);
        validateUsernameUniqueness(errors, username);

        // Check for validation errors
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(errors));
        }

        userService.registerUser(username, password, email);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    private void validateEmailUniqueness(Errors errors, String normalizedEmail) {
        if (userRepository.findByEmailIgnoreCase(normalizedEmail).isPresent()) {
            errors.rejectValue("email", "email", "Email is already in use");
        }
    }

    private void validateUsernameUniqueness(Errors errors, String username) {
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            errors.rejectValue("username", "username", "Username is already taken");
        }
    }

    private Map<String, String> getValidationErrors(Errors errors) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}
