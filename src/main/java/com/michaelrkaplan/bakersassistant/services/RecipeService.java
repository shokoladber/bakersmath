package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import com.michaelrkaplan.bakersassistant.models.Recipe;
import com.michaelrkaplan.bakersassistant.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    public void saveRecipe(Recipe recipe) {

        recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe createRecipe(Recipe recipe) {

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientService.saveIngredient(ingredient);
        }

        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    // Update fields of existingRecipe with data from updatedRecipe
                    existingRecipe.setName(updatedRecipe.getName());
                    existingRecipe.setIngredients(updatedRecipe.getIngredients());
                    existingRecipe.setInstructions(updatedRecipe.getInstructions());
                    // Update other fields as needed
                    return recipeRepository.save(existingRecipe);
                })
                .orElse(null); // Handle the case where the recipe with the given id is not found
    }

    public boolean deleteRecipe(Long id) {
        // Additional logic, validation, or processing can be added here
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
