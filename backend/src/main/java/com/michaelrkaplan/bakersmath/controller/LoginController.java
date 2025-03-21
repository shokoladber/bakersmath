package com.michaelrkaplan.bakersmath.controller;

import com.michaelrkaplan.bakersmath.dto.LoginForm;
import com.michaelrkaplan.bakersmath.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Attempt authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Load UserDetails for authenticated user
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        // Additional actions after successful authentication, such as redirecting
        return "redirect:/home"; // Redirect to home page after successful login
    }

    @GetMapping("/logout")
    public String logout() {
        // Clear the authentication from the SecurityContext
        SecurityContextHolder.clearContext();

        // Redirect to the login page or any other page after logout
        return "redirect:/login?logout";
    }


}
