package com.michaelrkaplan.bakersassistant.controllers;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import com.michaelrkaplan.bakersassistant.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.michaelrkaplan.bakersassistant.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/index")
    public String showRecipeIndex(Model model) {
        // Retrieve a list of recipes from your data source
        List<Recipe> recipes = recipeService.getAllRecipes();

        // Pass the list of recipes to the Thymeleaf template
        model.addAttribute("recipes", recipes);
        return "recipes/index";
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

        return "recipes/details";
    }

    @GetMapping("/add")
    public String showAddRecipeForm(Model model) {
        // Create a new Recipe object and add it to the model
        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>()); // Initialize the ingredients list
        model.addAttribute("recipe", new Recipe());

        return "recipes/add";
    }

    @PostMapping("/recipes/add")
     public String submitAddRecipeForm(@ModelAttribute Recipe recipe, Model model) {
        // Assuming RecipeService has a method to save the recipe
        recipeService.saveRecipe(recipe);

        // Redirect to the recipe index page or another appropriate view
        return "redirect:/recipes/index";
     }

}
