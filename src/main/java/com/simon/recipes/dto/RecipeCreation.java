package com.simon.recipes.dto;

import com.simon.recipes.entity.Recipe;

public record RecipeCreation(String userId, String recipeName, String recipeDesc) {
}
