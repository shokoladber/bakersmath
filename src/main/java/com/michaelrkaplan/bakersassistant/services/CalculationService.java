package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import com.michaelrkaplan.bakersassistant.models.Recipe;
import com.michaelrkaplan.bakersassistant.models.UnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.michaelrkaplan.bakersassistant.services.ConversionService.convertToGrams;

@Service
public class CalculationService {

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

    public Recipe scaleRecipe(Recipe originalRecipe, int batchSizeMultiplier) {
        // Create a new recipe to store scaled ingredients
        Recipe scaledRecipe = new Recipe();

        // Scale each ingredient and add to the scaled recipe
        for (Ingredient originalIngredient : originalRecipe.getIngredients()) {
            Ingredient scaledIngredient = scaleIngredient(originalIngredient, batchSizeMultiplier);
            scaledRecipe.addIngredient(scaledIngredient);
        }

        return scaledRecipe;
    }

    private Ingredient scaleIngredient(Ingredient originalIngredient, int batchSizeMultiplier) {
        // Create a new ingredient to store scaled details
        Ingredient scaledIngredient = new Ingredient();

        // Scale the ingredient details
        scaledIngredient.setName(originalIngredient.getName());
        double scaledQuantity = originalIngredient.getQuantity() * batchSizeMultiplier;
        scaledIngredient.setQuantity(scaledQuantity);

        // You may need to handle scaling for other properties like unit, etc.

        return scaledIngredient;
    }

}

