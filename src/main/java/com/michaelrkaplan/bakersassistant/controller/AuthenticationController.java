package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.dto.LoginForm;
import com.michaelrkaplan.bakersassistant.dto.RegistrationForm;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.CustomUserDetailsImpl;
import com.michaelrkaplan.bakersassistant.service.UserDetailsServiceImpl;
import com.michaelrkaplan.bakersassistant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());

    private final AuthenticationManager authenticationManager = new AuthenticationManager() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return null;
        }
    };

    @Autowired
    private UserService userService;

    private UserDetailsServiceImpl userDetailsServiceImp;

    private CustomUserDetailsImpl customUserDetails;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationForm registrationForm,
                               Model model,
                               Errors errors) {

        String username = registrationForm.getUsername();
        String email = registrationForm.getEmail();
        String password = registrationForm.getPassword();

        // Convert email to lowercase for case-insensitive comparison
        String normalizedEmail = email.toLowerCase();

        // Validate email uniqueness
        validateEmailUniqueness(errors, normalizedEmail);

        // Validate username uniqueness
        validateUsernameUniqueness(errors, username);

        // Check for validation errors
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "register";
        }

        userService.registerUser(username, password, email);

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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm, Model model) {
        // Create an authentication request using the provided username and password
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());

        try {
            // Attempt to authenticate the user using the AuthenticationManager
            Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

            // If authentication is successful, set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

            // Redirect to the home page or any other success page
            return "redirect:/home";
        } catch (AuthenticationException e) {
            // If authentication fails, add an error message to the model and return to the login page
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }



}
