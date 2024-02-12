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
}

