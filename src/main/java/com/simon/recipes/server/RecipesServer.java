package com.simon.recipes.server;

import com.simon.recipes.dto.RecipeCreation;
import com.simon.recipes.dto.UserInfo;
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

    @GetMapping("/test")
    public String testApi() {
        return "The API is working!";
    }

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
    public void createNewRecipe(@RequestBody RecipeCreation recipeCreation) {
        Optional<User> user = this.getUserById(recipeCreation.userId());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist!");
        Recipe newRecipe = new Recipe(recipeCreation.recipeName(), recipeCreation.recipeDesc(), user.get());
        this.service.saveRecipe(newRecipe);
    }
}
