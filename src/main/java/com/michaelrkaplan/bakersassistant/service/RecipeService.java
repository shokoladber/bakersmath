package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.model.CustomUserDetailsImpl;
import com.michaelrkaplan.bakersassistant.model.Ingredient;
import com.michaelrkaplan.bakersassistant.model.Recipe;
import com.michaelrkaplan.bakersassistant.model.User;
import com.michaelrkaplan.bakersassistant.repository.RecipeRepository;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final UserRepository userRepository;

    private final RecipeRepository recipeRepository;

    private final IngredientService ingredientService;

    @Autowired
    public RecipeService(UserRepository userRepository, RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.userRepository = userRepository;
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

    @Transactional
    public Recipe createRecipe(@NotNull Recipe recipe, Principal principal) {
        // Ensure principal is not null
        if (principal != null) {
            // Get the username from the principal
            String username = principal.getName();

            // Use UserService or UserRepository to find the user by username
            Optional<User> currentUserOptional = userRepository.findByUsernameIgnoreCase(username);

            // Check if the user exists
            if (currentUserOptional.isPresent()) {
                User currentUser = currentUserOptional.get();

                // Associate the recipe with the user
                recipe.setUser(currentUser);

                // Set the recipe for each ingredient
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredient.setRecipe(recipe);
                }

                // Add the recipe to the user's list of recipes
                currentUser.addRecipe(recipe);

                // Save the recipe to the database
                return recipeRepository.save(recipe);

            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

        } else {
            // Handle the case where the principal is null (optional)
            throw new IllegalStateException("User must be logged in to create a recipe.");
        }
    }

    @Transactional
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

    public Optional<Recipe> getRecipeByName(String recipeName) {
        return recipeRepository.findByName(recipeName);
    }

//    public boolean existsRecipeByNameIgnoreCase(String recipeName) {
//        // Use your repository method to check if a recipe with the given name exists
//        return recipeRepository.existsByNameIgnoreCase(recipeName);
//    }

    public boolean existsRecipeByNameIgnoreCaseAndUser(String name, User currentUser) {
        // Use your repository method to check if a recipe with the given name exists for the current user
        return recipeRepository.existsByNameIgnoreCaseAndUser(name, currentUser);
    }

    public Optional<Recipe> getRecipeByNameAndUser(String recipeName, String username) {
        Optional<User> optionalUser = userRepository.findByUsernameIgnoreCase(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return recipeRepository.findByNameAndUser(recipeName, user);
        } else {
            return Optional.empty();
        }
    }

}
