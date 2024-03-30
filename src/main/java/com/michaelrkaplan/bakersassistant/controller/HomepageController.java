package com.michaelrkaplan.bakersassistant.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class HomepageController {

    @GetMapping()
    public String showHomepage(Model model, Principal principal) {

        model.addAttribute("homepageTitle", "Baker's Math");
        model.addAttribute("principal", principal);

        return "homepage";
    }
}

