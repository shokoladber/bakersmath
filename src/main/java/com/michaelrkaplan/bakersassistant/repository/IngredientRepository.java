package com.michaelrkaplan.bakersassistant.repository;

import com.michaelrkaplan.bakersassistant.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
