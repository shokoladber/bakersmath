package com.michaelrkaplan.bakersmath.service;

import com.michaelrkaplan.bakersmath.model.Ingredient;
import com.michaelrkaplan.bakersmath.model.Recipe;
import com.michaelrkaplan.bakersmath.model.UnitType;
import com.michaelrkaplan.bakersmath.model.User;
import com.michaelrkaplan.bakersmath.repository.RecipeRepository;
import com.michaelrkaplan.bakersmath.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class CalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private RecipeRepository recipeRepository;

    public double calculateTotalWeightInGrams(Recipe recipe) {
        double totalWeight = 0.0;

        for (Ingredient ingredient : recipe.getIngredients()) {
            totalWeight += conversionService.convertToGrams(ingredient.getQuantity(), ingredient.getUnit());
        }

        return totalWeight;
    }

    public double calculateTotalWeight(Recipe recipe, UnitType targetUnit) {
        double totalWeightInGrams = calculateTotalWeightInGrams(recipe);
        return conversionService.convert(totalWeightInGrams, UnitType.grams, targetUnit);
    }

    public Recipe scaleRecipe(Recipe originalRecipe, ScalingMethod scalingMethod, Object... args) {
        switch (scalingMethod) {
            case BY_BATCH_SIZE:
                if (args.length < 2) {
                    throw new IllegalArgumentException("Insufficient arguments for scaling by batch size");
                }
                Double batchSizeMultiplier;
                String username;
                try {
                    batchSizeMultiplier = Double.parseDouble(args[0].toString().replaceAll("[^0-9.]", ""));
                    username = args[1].toString().replaceAll("[^a-zA-Z ]", ""); // Remove non-alphabetic characters
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Invalid arguments for scaling by batch size");
                }
                return scaleRecipeByBatchSize(originalRecipe, batchSizeMultiplier, username);
            case BY_TOTAL_WEIGHT:
                if (args.length < 2 || !(args[0] instanceof Double) || !(args[1] instanceof UnitType)) {
                    throw new IllegalArgumentException("Invalid arguments for scaling by total weight");
                }
                return scaleRecipeByTotalWeight(originalRecipe, (double) args[0], (UnitType) args[1]);
            default:
                throw new IllegalArgumentException("Unsupported scaling method");
        }
    }




    public Recipe scaleRecipeByBatchSize(Recipe originalRecipe,
                                         double batchSizeMultiplier,
                                         String username) {
        if (batchSizeMultiplier <= 0) {
            throw new IllegalArgumentException("Invalid batch size multiplier for scaling recipe");
        }

        // Create a new recipe to store scaled ingredients
        Recipe scaledRecipe = new Recipe();

        // Check if a recipe with the same name already exists
        boolean recipeExists = recipeRepository.existsByNameIgnoreCase(originalRecipe.getName() + " x" + batchSizeMultiplier);
        if (recipeExists) {
            throw new IllegalArgumentException("Recipe with the same name already exists");
        }

        // Set name and instructions of scaledIngredient
        scaledRecipe.setName(originalRecipe.getName() + " x" + batchSizeMultiplier);
        scaledRecipe.setInstructions(originalRecipe.getInstructions());

        // Set the user of the scaled recipe
        Optional<User> optionalUser = userRepository.findByUsernameIgnoreCase(username);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("User not found"));
        scaledRecipe.setUser(user);

        // Scale each ingredient and add to the scaled recipe
        for (Ingredient originalIngredient : originalRecipe.getIngredients()) {
            Ingredient scaledIngredient = scaleIngredientByBatchSize(originalIngredient, batchSizeMultiplier);

            // Set the recipe of the scaled ingredient to the scaled recipe
            scaledIngredient.setRecipe(scaledRecipe);

            // Add the scaled ingredient to the scaled recipe
            scaledRecipe.addIngredient(scaledIngredient);
        }

        // Save the scaled recipe
        recipeRepository.save(scaledRecipe);

        // Logging statements
        LOGGER.info("Scaling recipe: {} with id: {} to a new recipe with id: {}", originalRecipe.getName(), originalRecipe.getId(), scaledRecipe.getId());

        return scaledRecipe;
    }

    public Recipe scaleRecipeByTotalWeight(Recipe originalRecipe, double desiredTotalWeight, UnitType targetUnit) {
        if (desiredTotalWeight <= 0) {
            throw new IllegalArgumentException("Invalid desired total weight for scaling recipe");
        }

        // Create a new recipe to store scaled ingredients
        Recipe scaledRecipe = new Recipe();

        // Set name and instructions of scaledIngredient
        scaledRecipe.setName(originalRecipe.getName() + " (Scaled by Total Weight)");
        scaledRecipe.setInstructions(originalRecipe.getInstructions());

        // Calculate the scaling factor based on desired total weight
        double currentTotalWeight = calculateTotalWeightInGrams(originalRecipe);
        double scalingFactor = desiredTotalWeight / currentTotalWeight;

        // Scale each ingredient and add to the scaled recipe
        for (Ingredient originalIngredient : originalRecipe.getIngredients()) {
            Ingredient scaledIngredient = scaleIngredientByTotalWeight(originalIngredient, scalingFactor, targetUnit);

            // Set the recipe of the scaled ingredient to the scaled recipe
            scaledIngredient.setRecipe(scaledRecipe);

            // Add the scaled ingredient to the scaled recipe
            scaledRecipe.addIngredient(scaledIngredient);
        }

        // Save the scaled recipe
        recipeRepository.save(scaledRecipe);

        // Logging statements
        LOGGER.info("Scaling recipe: {} with id: {} to a new recipe with id: {}", originalRecipe.getName(), originalRecipe.getId(), scaledRecipe.getId());

        return scaledRecipe;
    }

    private Ingredient scaleIngredientByBatchSize(Ingredient originalIngredient, double batchSizeMultiplier) {
        // Create a new ingredient to store scaled details
        Ingredient scaledIngredient = new Ingredient();

        // Scale the ingredient details
        scaledIngredient.setName(originalIngredient.getName());
        double scaledQuantity = originalIngredient.getQuantity() * batchSizeMultiplier;
        scaledIngredient.setQuantity(scaledQuantity);

        // Set UnityType of the ingredient
        UnitType scaledUnit = originalIngredient.getUnit();
        scaledIngredient.setUnit(scaledUnit);

        // Logging statements
        LOGGER.info("Scaling ingredient: {} from {} to {}", originalIngredient.getName(), originalIngredient.getQuantity(), scaledQuantity);

        return scaledIngredient;
    }

    private Ingredient scaleIngredientByTotalWeight(Ingredient originalIngredient, double scalingFactor, UnitType targetUnit) {
        // Create a new ingredient to store scaled details
        Ingredient scaledIngredient = new Ingredient();

        // Scale the ingredient details
        scaledIngredient.setName(originalIngredient.getName());
        double scaledQuantity = originalIngredient.getQuantity() * scalingFactor;
        scaledIngredient.setQuantity(scaledQuantity);

        // Set UnityType of the ingredient
        UnitType scaledUnit = targetUnit;  // Use the specified targetUnit for scaling by total weight
        scaledIngredient.setUnit(scaledUnit);

        // Logging statements
        LOGGER.info("Scaling ingredient: {} from {} to {}", originalIngredient.getName(), originalIngredient.getQuantity(), scaledQuantity);

        return scaledIngredient;
    }

}

