package com.michaelrkaplan.bakersmath.repository;

import com.michaelrkaplan.bakersmath.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
