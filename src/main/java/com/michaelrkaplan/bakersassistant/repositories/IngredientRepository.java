package com.michaelrkaplan.bakersassistant.repositories;

import com.michaelrkaplan.bakersassistant.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
