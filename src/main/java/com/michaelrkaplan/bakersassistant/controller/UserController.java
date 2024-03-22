package com.michaelrkaplan.bakersassistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userDashboard() {
        return "user/dashboard";
    }

}
