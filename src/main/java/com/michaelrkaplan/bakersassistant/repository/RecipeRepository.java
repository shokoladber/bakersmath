package com.michaelrkaplan.bakersassistant.repository;

import com.michaelrkaplan.bakersassistant.model.Recipe;
import com.michaelrkaplan.bakersassistant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByName(String recipeName);

    boolean existsByNameIgnoreCase(String recipeName);

    List<Recipe> findByUserId(Long userId);

    boolean existsByNameIgnoreCaseAndUser(String name, User currentUser);
}
