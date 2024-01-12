package com.michaelrkaplan.bakersassistant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @GetMapping("/add")
    public String showAddIngredientForm() {
        return "ingredients/add";
    }

    // Add a method to handle form submission (you will need to implement this based on your application's logic)
    // For example:
    // @PostMapping("/add")
    // public String addIngredient(@ModelAttribute IngredientForm ingredientForm, Model model) {
    //     // Process the form submission (save to the database, etc.)
    //     // Redirect to a success page or show a confirmation message
    //     return "redirect:/ingredients/success";
    // }

    // Add more methods as needed for your application
}