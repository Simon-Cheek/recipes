package com.simon.recipes.repository;

import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByNameAndUser(String name, User user);
}
