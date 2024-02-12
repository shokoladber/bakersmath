package com.michaelrkaplan.bakersassistant.services;

import com.michaelrkaplan.bakersassistant.models.UnitType;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    public static double convertToGrams(double quantity, UnitType fromUnit) {
        switch (fromUnit) {
            case grams:
                return quantity;
            case pounds:
                return poundsToGrams(quantity);
            // Add more cases for other units
            default:
                throw new UnsupportedOperationException("Unsupported unit for conversion: " + fromUnit);
        }
    }

    public static double convertFromGrams(double quantity, UnitType toUnit) {
        switch (toUnit) {
            case grams:
                return quantity;
            case pounds:
                return gramsToPounds(quantity);
            // Add more cases for other units
            default:
                throw new UnsupportedOperationException("Unsupported unit for conversion: " + toUnit);
        }
    }

    private static double poundsToGrams(double pounds) {
        // Conversion factor from pounds to grams
        double poundsToGramsConversionFactor = 453.59237;
        return pounds * poundsToGramsConversionFactor;
    }

    private static double gramsToPounds(double grams) {
        // Conversion factor from grams to pounds
        double gramsToPoundsConversionFactor = 0.00220462;
        return grams * gramsToPoundsConversionFactor;
    }
}
