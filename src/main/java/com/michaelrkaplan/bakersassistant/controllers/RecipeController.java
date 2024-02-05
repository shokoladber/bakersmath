package com.michaelrkaplan.bakersassistant.controllers;

import com.michaelrkaplan.bakersassistant.models.Recipe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.michaelrkaplan.bakersassistant.services.RecipeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // Display an individual recipe page
    @GetMapping("/{recipeName}")
    public String showRecipeDetails(@PathVariable String recipeName, Model model) {

        Optional<Recipe> optionalRecipe = recipeService.getRecipeByName(recipeName);

        if (!optionalRecipe.isPresent()) {
            return "redirect:/recipes/index";
        } else {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipeName", recipe.getName());
            model.addAttribute("ingredients", recipe.getIngredients());
            model.addAttribute("instructions", recipe.getInstructions());
        }

        return "recipes/selected-recipe";
    }

    @GetMapping("/add")
    public String showAddRecipeForm(Model model) {
        // Create a new Recipe object and add it to the model
        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>()); // Initialize the ingredients list
        model.addAttribute("recipe", new Recipe());

        return "recipes/add";
    }

    @PostMapping("/add")
    public String submitAddRecipeForm(@ModelAttribute @Valid Recipe recipe, Model model) {
        // Validate the form fields using the @Valid annotation

        // Check if a recipe with the same name already exists (case-insensitive)
        if (recipeService.existsRecipeByNameIgnoreCase(recipe.getName())) {
            // Add an error message to the model
            model.addAttribute("recipeNameError", "Recipe name already exists.");

            // Return to the form page
            return "recipes/add";
        }

        // Save the recipe to the database
        recipeService.saveRecipe(recipe);

        // Redirect to the recipe index page or another appropriate view
        return "redirect:/recipes/index";
    }



    @GetMapping("/{recipeName}/delete")
    public String showDeleteRecipeForm(@PathVariable String recipeName, Model model) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipeByName(recipeName);

        if (!optionalRecipe.isPresent()) {
            return "redirect:/recipes/index";
        } else {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipeName", recipe.getName());
            model.addAttribute("recipeId", recipe.getId());
        }

        return "recipes/delete";
    }

    @PostMapping("/delete")
    public String submitDeleteRecipeForm(@RequestParam Long recipeId) {
        boolean isDeleted = recipeService.deleteRecipe(recipeId);

        if (isDeleted) {
            // Redirect to the recipe index page or another appropriate view
            return "redirect:/recipes/index";
        } else {
            // Handle the case where the recipe couldn't be deleted
            return "redirect:/recipes/index";
        }
    }

}
