package com.michaelrkaplan.bakersassistant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @GetMapping("/index-recipes")
    public String showRecipeIndex(Model model) {
        // Logic to retrieve a list of recipes from your data source
        // For simplicity, let's assume we have a service that provides the data
        // Replace this with your actual service call
        // List<Recipe> recipes = recipeService.getAllRecipes();

        // For demonstration purposes, create a sample list of recipe names
        // Replace this with your actual data
        model.addAttribute("recipeNames", List.of("Recipe 1", "Recipe 2", "Recipe 3"));

        return "recipes/index-recipes";
    }

    // Example method for displaying an individual recipe page
    @GetMapping("/{recipeName}")
    public String showRecipeDetails(Model model) {
        // Logic to retrieve the details of a specific recipe from your data source
        // For simplicity, let's assume we have a service that provides the data
        // Replace this with your actual service call
        // Recipe recipe = recipeService.getRecipeByName(recipeName);

        // For demonstration purposes, create a sample recipe
        // Replace this with your actual data
        model.addAttribute("recipeName", "Recipe Name");
        model.addAttribute("ingredients", List.of("Ingredient 1", "Ingredient 2", "Ingredient 3"));
        model.addAttribute("instructions", List.of("Step 1: Instruction 1", "Step 2: Instruction 2", "Step 3: Instruction 3"));

        return "recipe/details";
    }
}
