package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import com.michaelrkaplan.bakersassistant.models.Recipe;
import com.michaelrkaplan.bakersassistant.models.UnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalculationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationService.class);

    @Autowired
    private ConversionService conversionService;

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

    @Transactional
    public Recipe scaleRecipe(Recipe originalRecipe, int batchSizeMultiplier) {
        // Create a new recipe to store scaled ingredients
        Recipe scaledRecipe = new Recipe();

        // Set name and instructions of scaledIngredient
        scaledRecipe.setName(originalRecipe.getName() + " x " + batchSizeMultiplier);
        scaledRecipe.setInstructions(originalRecipe.getInstructions());

        // Scale each ingredient and add to the scaled recipe
        for (Ingredient originalIngredient : originalRecipe.getIngredients()) {
            Ingredient scaledIngredient = scaleIngredient(originalIngredient, batchSizeMultiplier);

            // Set the recipe of the scaled ingredient to the scaled recipe
            scaledIngredient.setRecipe(scaledRecipe);

            // Add the scaled ingredient to the scaled recipe
            scaledRecipe.addIngredient(scaledIngredient);
        }

        // Logging statements
        LOGGER.info("Scaling recipe: {} with id: {} to a new recipe with id: {}", originalRecipe.getName(), originalRecipe.getId(), scaledRecipe.getId());

        return scaledRecipe;
    }


    private Ingredient scaleIngredient(Ingredient originalIngredient, int batchSizeMultiplier) {
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

}

