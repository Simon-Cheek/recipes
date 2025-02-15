package com.simon.recipes.service;

import com.simon.recipes.dto.ItemDTO;
import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.Category;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;

import java.util.Optional;

public interface RecipesService {

    User getUser(int userId);

    User getFullUser(int userId);

    User getUser(String username);

    int saveUser(User user);

    int createUser(UserInfo user);

    void deleteUser(int userId);

    Optional<Recipe> getRecipe(int recipeId);

    int createRecipe(ItemDTO recipeDTO);

    int saveRecipe(Recipe recipe);

    int deleteRecipe(int recipeId);

    int createCategory(ItemDTO categoryDTO);

    Optional<Category> getCategory(int categoryId);

    int saveCategory(Category category);

    int deleteCategory(int categoryId);

    void addRecipeToCategory(int recipeId, int categoryId);
//
//    void deleteRecipeFromCategory(int recipeId, int categoryId);
//
//    void addCategoryToRecipe(int categoryId, int recipeId);
//
//    void deleteCategoryFromRecipe(int categoryId, int recipeId);

}
