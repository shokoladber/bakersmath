package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.UnitType;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    public static double convertToGrams(double quantity, UnitType unit) {
        switch (unit) {
            case grams:
                return quantity;
            case pounds:
                return poundsToGrams(quantity);
            // Add more cases for other units
            default:
                throw new UnsupportedOperationException("Unsupported unit for conversion: " + unit);
        }
    }

    private static double poundsToGrams(double pounds) {
        // Conversion factor from pounds to grams
        double poundsToGramsConversionFactor = 453.59237;
        return pounds * poundsToGramsConversionFactor;
    }
}
