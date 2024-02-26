package com.michaelrkaplan.bakersassistant.service;

import com.michaelrkaplan.bakersassistant.model.UnitType;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    public double convert(double quantity, UnitType fromUnit, UnitType toUnit) {
        if (fromUnit == toUnit) {
            return quantity; // No conversion needed
        }

        double grams = convertToGrams(quantity, fromUnit);
        return convertFromGrams(grams, toUnit);
    }

    public static double convertToGrams(double quantity, UnitType fromUnit) {
        switch (fromUnit) {
            case grams:
                return quantity;
            case ounces:
                return ouncesToGrams(quantity);
            case milliliters:
                return millilitersToGrams(quantity);
            case liters:
                return litersToGrams(quantity);
            case cups:
                return cupsToGrams(quantity);
            case teaspoons:
                return teaspoonsToGrams(quantity);
            case tablespoons:
                return tablespoonsToGrams(quantity);
            case pints:
                return pintsToGrams(quantity);
            case quarts:
                return quartsToGrams(quantity);
            case gallons:
                return gallonsToGrams(quantity);
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
            case ounces:
                return gramsToOunces(quantity);
            case milliliters:
                return gramsToMilliliters(quantity);
            case liters:
                return gramsToLiters(quantity);
            case cups:
                return gramsToCups(quantity);
            case teaspoons:
                return gramsToTeaspoons(quantity);
            case tablespoons:
                return gramsToTablespoons(quantity);
            case pints:
                return gramsToPints(quantity);
            case quarts:
                return gramsToQuarts(quantity);
            case gallons:
                return gramsToGallons(quantity);
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

    private static double ouncesToGrams(double ounces) {
        // Conversion factor from ounces to grams
        double ouncesToGramsConversionFactor = 28.3495;
        return ounces * ouncesToGramsConversionFactor;
    }

    private static double gramsToOunces(double grams) {
        // Conversion factor from grams to ounces
        double gramsToOuncesConversionFactor = 0.03527396;
        return grams * gramsToOuncesConversionFactor;
    }

    private static double millilitersToGrams(double milliliters) {
        // Conversion factor from milliliters to grams (assuming water density)
        double millilitersToGramsConversionFactor = 1.0;
        return milliliters * millilitersToGramsConversionFactor;
    }

    private static double gramsToMilliliters(double grams) {
        // Conversion factor from grams to milliliters (assuming water density)
        double gramsToMillilitersConversionFactor = 1.0;
        return grams * gramsToMillilitersConversionFactor;
    }

    private static double litersToGrams(double liters) {
        // Conversion factor from liters to grams (assuming water density)
        double litersToGramsConversionFactor = 1000.0;
        return liters * litersToGramsConversionFactor;
    }

    private static double gramsToLiters(double grams) {
        // Conversion factor from grams to liters (assuming water density)
        double gramsToLitersConversionFactor = 0.001;
        return grams * gramsToLitersConversionFactor;
    }

    private static double cupsToGrams(double cups) {
        // Conversion factor from cups to grams (assuming common ingredient density)
        double cupsToGramsConversionFactor = 240.0;
        return cups * cupsToGramsConversionFactor;
    }

    private static double gramsToCups(double grams) {
        // Conversion factor from grams to cups (assuming common ingredient density)
        double gramsToCupsConversionFactor = 0.00416667;
        return grams * gramsToCupsConversionFactor;
    }

    private static double teaspoonsToGrams(double teaspoons) {
        // Conversion factor from teaspoons to grams (assuming common ingredient density)
        double teaspoonsToGramsConversionFactor = 4.92892;
        return teaspoons * teaspoonsToGramsConversionFactor;
    }

    private static double gramsToTeaspoons(double grams) {
        // Conversion factor from grams to teaspoons (assuming common ingredient density)
        double gramsToTeaspoonsConversionFactor = 0.202884;
        return grams * gramsToTeaspoonsConversionFactor;
    }

    private static double tablespoonsToGrams(double tablespoons) {
        // Conversion factor from tablespoons to grams (assuming common ingredient density)
        double tablespoonsToGramsConversionFactor = 14.7868;
        return tablespoons * tablespoonsToGramsConversionFactor;
    }

    private static double gramsToTablespoons(double grams) {
        // Conversion factor from grams to tablespoons (assuming common ingredient density)
        double gramsToTablespoonsConversionFactor = 0.067628;
        return grams * gramsToTablespoonsConversionFactor;
    }

    private static double pintsToGrams(double pints) {
        // Conversion factor from pints to grams (assuming common ingredient density)
        double pintsToGramsConversionFactor = 473.176;
        return pints * pintsToGramsConversionFactor;
    }

    private static double gramsToPints(double grams) {
        // Conversion factor from grams to pints (assuming common ingredient density)
        double gramsToPintsConversionFactor = 0.00211338;
        return grams * gramsToPintsConversionFactor;
    }

    private static double quartsToGrams(double quarts) {
        // Conversion factor from quarts to grams (assuming common ingredient density)
        double quartsToGramsConversionFactor = 946.353;
        return quarts * quartsToGramsConversionFactor;
    }

    private static double gramsToQuarts(double grams) {
        // Conversion factor from grams to quarts (assuming common ingredient density)
        double gramsToQuartsConversionFactor = 0.00105669;
        return grams * gramsToQuartsConversionFactor;
    }

    private static double gallonsToGrams(double gallons) {
        // Conversion factor from gallons to grams (assuming common ingredient density)
        double gallonsToGramsConversionFactor = 3785.41;
        return gallons * gallonsToGramsConversionFactor;
    }

    private static double gramsToGallons(double grams) {
        // Conversion factor from grams to gallons (assuming common ingredient density)
        double gramsToGallonsConversionFactor = 0.000264172;
        return grams * gramsToGallonsConversionFactor;
    }


}
