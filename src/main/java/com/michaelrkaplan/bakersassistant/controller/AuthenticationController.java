package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.dto.UserRequest;
import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String email, @RequestParam String password) {
        // Check if the email is already taken
        if (userRepository.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        // Create a new user
        User user = new User();
        user.setEmail(email);
        // Hash the password before saving it
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestBody UserRequest userRequest, Model model) {
        // Logic to authenticate user using userRequest.getEmail() and userRequest.getPassword()
        if (userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword())) {
            // Redirect to a success page or perform other actions
            return "redirect:/home";
        } else {
            // If authentication fails, add an error message to the model and return to the login page
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

}
