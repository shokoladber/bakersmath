package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.dto.LoginForm;
import com.michaelrkaplan.bakersassistant.service.CustomUserDetailsImpl;
import com.michaelrkaplan.bakersassistant.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.logging.Logger;


@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm,
                               BindingResult bindingResult,
                               Model model) {

        Logger logger = Logger.getLogger(this.getClass().getName());

        logger.info("Starting login form processing...");

        // Injected AuthenticationManager bean
        AuthenticationManager authentication = authenticationManager;


        if (bindingResult.hasErrors()) {
            logger.severe("Login form validation failed: " + bindingResult.getAllErrors());
            return "login";
        }

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Create an authentication request using the provided username and password
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            // Attempt to authenticate the user using the AuthenticationManager
            Authentication authenticationResponse = authentication.authenticate(authenticationRequest);

            logger.info("Authentication successful for user: " + username);

            // If authentication is successful, set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

            // Get the authenticated user's authorities (roles)
            Collection<? extends GrantedAuthority> authorities = authenticationResponse.getAuthorities();

            // Redirect users based on their roles
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                // Redirect admin users to the admin dashboard
                logger.info("Redirecting admin user to admin dashboard");
                return "redirect:/admin/dashboard";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                // Redirect regular users to the user dashboard
                logger.info("Redirecting regular user to user dashboard");
                return "redirect:/user/dashboard";
            } else {
                // If the user has no specific role, redirect to a generic homepage
                logger.warning("User " + username + " has no specific role, redirecting to home");
                return "redirect:/home";
            }
        } catch (AuthenticationException e) {
            // If authentication fails, add an error message to the model and return to the login page
            logger.severe("Authentication failed for user " + username + ": " + e.getMessage());
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }


}
