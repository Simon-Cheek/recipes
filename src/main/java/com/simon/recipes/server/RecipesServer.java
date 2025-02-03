package com.simon.recipes.server;

import com.simon.recipes.dto.ItemCreation;
import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.Category;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;
import com.simon.recipes.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipesServer {

    private final RecipesService service;

    public RecipesServer(RecipesService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }

    // TODO: Move Business logic to SERVICE layer
    @GetMapping("/user")
    public Optional<User> getUserById(@RequestParam(value = "id") String userId) {
        if (userId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id empty or not valid");
        Optional<User> user = service.getUser(Integer.parseInt(userId));
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return user;
    }

    @PostMapping("/user")
    public int createNewUser(@RequestBody UserInfo user) {
        User checkUser = this.service.getUser(user.username());
        if (checkUser != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        if (user.username() == null || user.password() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must include username and password");
        return this.service.createUser(user);
    }

    @PostMapping("/recipe")
    public void createNewRecipe(@RequestBody ItemCreation itemCreation) {
        Optional<User> user = this.getUserById(itemCreation.userId());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        Recipe newRecipe = new Recipe(itemCreation.itemName(), itemCreation.itemDesc(), user.get());
        this.service.saveRecipe(newRecipe);
    }

    @PutMapping("/recipe")
    public void updateRecipe(@RequestBody Recipe recipe) {
        if (recipe.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must include an ID field");
        Optional<Recipe> fetchedRecipe = this.service.getRecipe(recipe.getId());
        if (fetchedRecipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe does not exist");
        this.service.saveRecipe(recipe);
    }

    @DeleteMapping("/recipe")
    public void deleteRecipe(@RequestBody Integer recipeId) {
        Optional<Recipe> recipe = this.service.getRecipe(recipeId);
        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe does not exist");
        this.service.deleteRecipe(recipeId);
    }

    @PostMapping("/category")
    public void createNewCategory(@RequestBody ItemCreation itemCreation) {
        Optional<User> user = this.getUserById(itemCreation.userId());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        Category newCategory = new Category(itemCreation.itemName(), itemCreation.itemDesc(), user.get());
        this.service.saveCategory(newCategory);
    }

    @PutMapping("/category")
    public void updateCategory(@RequestBody Category category) {
        if (category.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must include an ID field");
        Optional<Category> fetchedCategory = this.service.getCategory(category.getId());
        if (fetchedCategory.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");
        this.service.saveCategory(category);
    }

    @DeleteMapping("/category")
    public void deleteCategory(@RequestBody Integer categoryId) {
        Optional<Category> category = this.service.getCategory(categoryId);
        if (category.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe does not exist");
        this.service.deleteRecipe(categoryId);
    }
}
