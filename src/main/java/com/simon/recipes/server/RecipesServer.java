package com.simon.recipes.server;

import com.simon.recipes.dto.ItemDTO;
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

    @GetMapping("/user")
    public User getUserById(@RequestParam(value = "id") int userId) {
        return service.getUser(userId);
    }

    @PostMapping("/user")
    public int createNewUser(@RequestBody UserInfo user) {
        return this.service.createUser(user);
    }

    @PostMapping("/recipe")
    public int createNewRecipe(@RequestBody ItemDTO recipeDTO) {
        return this.service.createRecipe(recipeDTO);
    }

    @PutMapping("/recipe")
    public int updateRecipe(@RequestBody Recipe recipe) {
        return this.service.saveRecipe(recipe);
    }

    @DeleteMapping("/recipe")
    public int deleteRecipe(@RequestBody Integer recipeId) {
        return this.service.deleteRecipe(recipeId);
    }

    @PostMapping("/category")
    public int createNewCategory(@RequestBody ItemDTO itemCreation) {
        return this.service.createCategory(itemCreation);
    }

    @PutMapping("/category")
    public int updateCategory(@RequestBody Category category) {
        return this.service.saveCategory(category);
    }

    @DeleteMapping("/category")
    public int deleteCategory(@RequestBody Integer categoryId) {
        return this.service.deleteCategory(categoryId);
    }
}
