package com.simon.recipes.service;

import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.Category;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;

import java.util.Optional;

public interface RecipesService {

    Optional<User> getUser(int userId);

    User getFullUser(int userId);

    User getUser(String username);

    int saveUser(User user);

    int createUser(UserInfo user);

    void deleteUser(int userId);

    Optional<Recipe> getRecipe(int recipeId);

    void saveRecipe(Recipe recipe);

    void deleteRecipe(int recipeId);

    Optional<Category> getCategory(int categoryId);

    void saveCategory(Category category);

    void deleteCategory(int categoryId);

}
