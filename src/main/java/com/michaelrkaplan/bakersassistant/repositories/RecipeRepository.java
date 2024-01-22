package com.michaelrkaplan.bakersassistant.repositories;

import com.michaelrkaplan.bakersassistant.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // You can add custom query methods here if needed
}
