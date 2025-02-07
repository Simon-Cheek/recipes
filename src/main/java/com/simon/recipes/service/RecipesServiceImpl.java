package com.simon.recipes.service;

import com.simon.recipes.dto.ItemDTO;
import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.Category;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;
import com.simon.recipes.exceptions.InvalidRequestException;
import com.simon.recipes.exceptions.MissingInfoException;
import com.simon.recipes.exceptions.ResourceNotFoundException;
import com.simon.recipes.repository.CategoryRepository;
import com.simon.recipes.repository.RecipeRepository;
import com.simon.recipes.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public User getUser(int userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getUser(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) { throw new ResourceNotFoundException("User not found"); }
        return user;
    }

    @Override
    @Transactional
    public User getFullUser(int userId) {

        Optional<User> optUser = this.userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.getRecipes(); // Trigger lazy loading
            user.getCategories();
            return user;
        } else return null;
    }

    @Override
    public int saveUser(User user) {
        return this.userRepository.save(user).getId();
    }

    @Override
    public int createUser(UserInfo user) {
        if (user == null || user.username() == null || user.password() == null || user.username().isEmpty()) {
            throw new MissingInfoException("Incomplete User");
        }
        User checkUser = this.userRepository.findByUsername(user.username());
        if (checkUser != null) { throw new InvalidRequestException("User already exists"); }
        return this.userRepository.save(new User(user.username(), user.password(), user.description())).getId();
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
    public int saveRecipe(Recipe recipe) {
        if (recipe == null || recipe.getName() == null) {
            throw new MissingInfoException("Incomplete Recipe");
        }
        this.recipeRepository.save(recipe);
        return recipe.getId();
    }

    @Override
    public int createRecipe(ItemDTO recipeCreation) {
        if (recipeCreation == null || recipeCreation.itemName() == null) {
            throw new MissingInfoException("Incomplete Recipe");
        }
        User user = this.getUser(recipeCreation.userId());
        Optional<Recipe> checkRecipe = this.recipeRepository.findByNameAndUser(recipeCreation.itemName(), user);
        if (checkRecipe.isPresent()) {
            throw new InvalidRequestException("Recipe already exists");
        }
        Recipe newRecipe = new Recipe(recipeCreation.itemName(), recipeCreation.itemDesc(), user);
        this.recipeRepository.save(newRecipe);
        return newRecipe.getId();
    }

    @Override
    public int createCategory(ItemDTO categoryCreation) {
        if (categoryCreation == null || categoryCreation.itemName() == null) {
            throw new MissingInfoException("Incomplete Recipe");
        }
        User user = this.getUser(categoryCreation.userId());
        Category newCategory = new Category(categoryCreation.itemName(), categoryCreation.itemDesc(), user);
        this.categoryRepository.save(newCategory);
        return newCategory.getId();
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
