package com.michaelrkaplan.bakersassistant.controller;

import com.michaelrkaplan.bakersassistant.model.*;
import com.michaelrkaplan.bakersassistant.repository.UserRepository;
import com.michaelrkaplan.bakersassistant.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("principal", principal);
        return "recipes/index";
    }


    // Display an individual recipe page
    @GetMapping("/{recipeName}")
    public String showRecipeDetails(@PathVariable String recipeName, Model model, Principal principal) {
        String username = principal.getName(); // Get the username of the logged-in user

        // Retrieve the recipe by name for the logged-in user
        Optional<Recipe> optionalRecipe = recipeService.getRecipeByNameAndUser(recipeName, username);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipeName", recipe.getName());
            model.addAttribute("ingredients", recipe.getIngredients());
            model.addAttribute("targetUnitType", "grams");
            model.addAttribute("instructions", recipe.getInstructions());
            model.addAttribute("principal", principal);

            // Calculate total weight of the recipe
            double totalWeight = calculationService.calculateTotalWeightInGrams(recipe);
            model.addAttribute("totalWeight", totalWeight);

            return "recipes/selected-recipe";
        } else {
            return "redirect:/recipes/index";
        }
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
    public String showDeleteRecipeForm(@PathVariable String recipeName,
                                       Model model,
                                       Principal principal) {
        String username = principal.getName();

        Optional<Recipe> optionalRecipe = recipeService.getRecipeByNameAndUser(recipeName, username);

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
                                         @RequestParam(required = false) String cancel,
                                         Principal principal) {

        String username = principal.getName(); // Get the username of the logged-in user

        if (cancel != null) {
            // Redirect to the recipe details page if the user cancels
            return "redirect:/recipes/{recipeName}/";
        }

        // Attempt to delete the recipe associated with the logged-in user
        boolean isDeleted = recipeService.deleteRecipe(recipeId, username);

        if (isDeleted) {
            // Redirect to the recipe index page after successful deletion
            return "redirect:/recipes/index";
        } else {
            // Redirect to the recipe details page if the recipe couldn't be deleted
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
            Principal principal,
            @RequestParam("recipeName") String recipeName,
            @RequestParam("scalingMethod") ScalingMethod scalingMethod,
            @RequestParam("args") Object[] args) {

        String username = principal.getName();
        Optional<Recipe> optionalRecipe = recipeService.getRecipeByNameAndUser(recipeName, username);

        if (!optionalRecipe.isPresent()) {
            // If the recipe with the given name is not found, return a 404 Not Found response.
            return ResponseEntity.notFound().build();
        }

        Recipe originalRecipe = optionalRecipe.get();
        Object[] methodArgs = Arrays.copyOfRange(args, 1, args.length); // Exclude the first element (scaling method)

        Recipe scaledRecipe = calculationService.scaleRecipe(originalRecipe, scalingMethod, methodArgs);
        return ResponseEntity.ok(scaledRecipe);
    }


}
