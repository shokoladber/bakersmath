package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.dto.UserRequest;
import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {

        // Convert email to lowercase for case-insensitive comparison
        String normalizedEmail = email.toLowerCase();

        // Check if the email is already taken
        Optional<User> existingUserOptional = userRepository.findByEmailIgnoreCase(normalizedEmail);
        if (existingUserOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        // Check if the username is already taken
        existingUserOptional = userRepository.findByUsernameIgnoreCase(username);
        if (existingUserOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Username is already in use");
        }

        // Create a new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(normalizedEmail);
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
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Load user details using UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Logic to authenticate user
        if (userDetails != null && userDetails.getPassword().equals(password)) {
            LOGGER.log(Level.INFO, "User authenticated successfully: " + username);

            // Manually set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );

            // Redirect to a success page or perform other actions
            return "redirect:/home";
        } else {
            LOGGER.log(Level.WARNING, "Authentication failed for user: " + username);

            // If authentication fails, add an error message to the model and return to the login page
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

}
