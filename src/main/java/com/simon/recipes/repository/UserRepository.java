package com.simon.recipes.repository;

import com.simon.recipes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.recipes r " +
            "LEFT JOIN FETCH r.recipeIngredients ri " +
            "LEFT JOIN FETCH ri.ingredient " +
            "WHERE u.id = :userId")
    User findUserWithRecipesAndIngredients(@Param("userId") int userId);
}
