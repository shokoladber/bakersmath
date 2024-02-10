package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import com.michaelrkaplan.bakersassistant.models.Recipe;
import org.springframework.stereotype.Service;

import static com.michaelrkaplan.bakersassistant.services.ConversionService.convertToGrams;

@Service
public class CalculationService {

    public double calculateTotalWeightInGrams(Recipe recipe) {
        double totalWeight = 0.0;

        for (Ingredient ingredient : recipe.getIngredients()) {
            totalWeight += convertToGrams(ingredient.getQuantity(), ingredient.getUnit());
        }

        return totalWeight;
    }

}
