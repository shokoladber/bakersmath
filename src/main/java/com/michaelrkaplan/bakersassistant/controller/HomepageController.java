package com.michaelrkaplan.bakersassistant.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomepageController {

    @GetMapping()
    public String showHomepage(Model model, Authentication authentication) {

        model.addAttribute("homepageTitle", "Baker's Math");
        model.addAttribute("authentication", authentication);

        return "homepage";
    }
}

