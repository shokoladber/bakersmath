package com.michaelrkaplan.bakersassistant.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userDashboard(Model model, Principal principal) {
        // Get the username of the logged-in user
        String username = principal.getName();

        // Pass the username to the dashboard template
        model.addAttribute("username", username);

        // You can also fetch additional user-specific data from the database
        // and pass it to the template

        return "user/dashboard";
    }

    @GetMapping("/logout")
    public RedirectView logout() {
        // Invalidate current session and clear security context
        SecurityContextHolder.getContext().setAuthentication(null);

        // Redirect to the login page or any other desired page
        return new RedirectView("/login?logout");
    }

}
