package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.model.*;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.CalculationService;
import com.michaelrkaplan.bakersassistant.service.ConversionService;
import com.michaelrkaplan.bakersassistant.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.michaelrkaplan.bakersassistant.service.RecipeService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserRepository userRepository;

    private final CalculationService calculationService;
    private final ConversionService conversionService;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            UserRepository userRepository,
                            CalculationService calculationService,
                            ConversionService conversionService) {
        this.recipeService = recipeService;
        this.userRepository = userRepository;
        this.calculationService = calculationService;
        this.conversionService = conversionService;
    }

    @GetMapping("index")
    public String viewRecipes(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> currentUserOptional = userRepository.findByUsernameIgnoreCase(username);
        User currentUser = currentUserOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        List<Recipe> userRecipes = currentUser.getRecipes();
        model.addAttribute("userRecipes", userRecipes);
        return "recipes/index";
    }


    // Display an individual recipe page
    @GetMapping("/{recipeName}")
    public String showRecipeDetails(@PathVariable String recipeName, Model model) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipeByName(recipeName);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipeName", recipe.getName());
            model.addAttribute("ingredients", recipe.getIngredients());
            model.addAttribute("targetUnitType", "grams");
            model.addAttribute("instructions", recipe.getInstructions());
            double totalWeight = calculationService.calculateTotalWeightInGrams(recipe);
            model.addAttribute("totalWeight", totalWeight);
        } else {
            return "redirect:/recipes/index";
        }
        return "recipes/selected-recipe";
    }

    @GetMapping("/add")
    public String showAddRecipeForm(Model model, Principal principal) {
        Recipe recipe = new Recipe();
        String username = principal.getName();
        User currentUser = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        recipe.setUser(currentUser);
        model.addAttribute("recipe", recipe);
        return "recipes/add";
    }

    @PostMapping("/add")
    public String submitAddRecipeForm(@ModelAttribute @Valid Recipe recipe, Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        recipe.setUser(currentUser);
        if (recipeService.existsRecipeByNameIgnoreCaseAndUser(recipe.getName(), currentUser)) {
            model.addAttribute("recipeNameError", "Recipe name already exists.");
            return "recipes/add";
        }
        recipeService.createRecipe(recipe, principal);
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes/index";
    }




    @GetMapping("/{recipeName}/delete")
    public String showDeleteRecipeForm(@PathVariable String recipeName, Model model) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipeByName(recipeName);

        if (!optionalRecipe.isPresent()) {
            return "redirect:/recipes/index";
        } else {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipeName", recipe.getName());
            model.addAttribute("recipeId", recipe.getId());
        }

        return "recipes/delete";
    }

    @PostMapping("/delete")
    public String submitDeleteRecipeForm(@RequestParam Long recipeId,
                                         @RequestParam(required = false) String cancel) {

        if (cancel != null) {
            return "redirect:/recipes/{recipeName}/";
        }

        boolean isDeleted = recipeService.deleteRecipe(recipeId);

        if (isDeleted) {
            return "redirect:/recipes/index";
        } else {
            // Handle the case where the recipe couldn't be deleted
            return "redirect:/recipes/{recipeName}";
        }
    }

    @GetMapping("/convert-weight")
    @ResponseBody
    public Map<String, Double> convertWeight(
            @RequestParam String unitType,
            @RequestParam String targetUnitType,
            @RequestParam double weight) {

        double convertedWeight = conversionService.convert(weight,
                UnitType.valueOf(unitType),
                UnitType.valueOf(targetUnitType));

        // Create a Map to hold the converted weight
        Map<String, Double> result = new HashMap<>();
        result.put("convertedWeight", convertedWeight);

        return result;
    }

    @GetMapping("/scale")
    @ResponseBody
    public ResponseEntity<Recipe> scaleRecipe(
            @RequestParam("recipeName") String recipeName,
            @RequestParam(value = "batchSizeMultiplier", required = false) Integer batchSizeMultiplier,
            @RequestParam(value = "desiredTotalWeight", required = false) Double desiredTotalWeight,
            @RequestParam(value = "targetUnit", required = false) UnitType targetUnit) {

        if ((batchSizeMultiplier == null && (desiredTotalWeight == null || targetUnit == null)) ||
                (batchSizeMultiplier != null && (desiredTotalWeight != null || targetUnit != null))) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Recipe> optionalRecipe = recipeService.getRecipeByName(recipeName);

        if (optionalRecipe.isPresent()) {
            Recipe originalRecipe = optionalRecipe.get();

            if (batchSizeMultiplier != null) {
                Recipe scaledRecipe = calculationService.scaleRecipeByBatchSize(originalRecipe, batchSizeMultiplier);
                return ResponseEntity.ok(scaledRecipe);
            } else {
                Recipe scaledRecipe = calculationService.scaleRecipeByTotalWeight(originalRecipe, desiredTotalWeight, targetUnit);
                return ResponseEntity.ok(scaledRecipe);
            }
        } else {
            // If the recipe with the given name is not found, you might want to return a 404 Not Found response.
            return ResponseEntity.notFound().build();
        }
    }

}
