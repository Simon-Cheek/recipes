package com.simon.recipes.server;

import com.simon.recipes.entity.User;
import com.simon.recipes.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipesServer {

    private final RecipesService service;

    public RecipesServer(RecipesService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public Optional<User> getUserById(@RequestParam(value = "id") String userId) {
        if (userId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id empty or not valid");
        Optional<User> user = service.getUser(Integer.parseInt(userId));
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return user;
    }
}
