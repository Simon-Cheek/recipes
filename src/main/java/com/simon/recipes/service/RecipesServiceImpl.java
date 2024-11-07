package com.simon.recipes.service;

import com.simon.recipes.entity.Category;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;
import com.simon.recipes.repository.CategoryRepository;
import com.simon.recipes.repository.RecipeRepository;
import com.simon.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipesServiceImpl implements RecipesService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipesServiceImpl(UserRepository ur, CategoryRepository cr, RecipeRepository rr) {
        this.userRepository = ur;
        this.categoryRepository = cr;
        this.recipeRepository = rr;
    }

    @Override
    public Optional<User> getUser(int userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public Optional<Recipe> getRecipe(int recipeId) {
        return this.recipeRepository.findById(recipeId);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        this.recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(int recipeId) {
        this.recipeRepository.deleteById(recipeId);
    }

    @Override
    public Optional<Category> getCategory(int categoryId) {
        return this.categoryRepository.findById(categoryId);
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }
}
