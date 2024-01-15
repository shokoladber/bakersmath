package com.michaelrkaplan.bakersassistant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomepageController {

    @GetMapping()
    public String showHomepage(Model model) {

        model.addAttribute("homepageTitle", "Baker's Assistant");

        return "homepage";
    }
}

