package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model,
                               Errors errors) {

        model.addAttribute("errors", errors);

        // Convert email to lowercase for case-insensitive comparison
        String normalizedEmail = email.toLowerCase();

        // Validate email uniqueness
        validateEmailUniqueness(errors, normalizedEmail);

        // Validate username uniqueness
        validateUsernameUniqueness(errors, username);

        // Check for validation errors
        if (errors.hasErrors()) {
            return "register";
        }

        // Create a new user
        User user = createUser(username, normalizedEmail, password);

        userRepository.save(user);

        return "redirect:/login";
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

    private User createUser(String username, String normalizedEmail, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(normalizedEmail);
        // Hash the password before saving it
        user.setPassword(passwordEncoder.encode(password));
        return user;
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
