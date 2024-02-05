package com.michaelrkaplan.bakersassistant.repositories;

import com.michaelrkaplan.bakersassistant.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByName(String recipeName);

    boolean existsByNameIgnoreCase(String recipeName);

}
