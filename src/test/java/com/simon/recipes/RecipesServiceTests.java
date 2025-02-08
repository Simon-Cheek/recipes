package com.simon.recipes;

import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.exceptions.ResourceNotFoundException;
import com.simon.recipes.repository.UserRepository;
import com.simon.recipes.service.RecipesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipesServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipesServiceImpl recipesService;

    // SERVICE TESTS
    @Test
    void createsAUser() {
        recipesService.createUser(new UserInfo("John1234", "Doe", "john@doe.com"));
        Assertions.assertNotNull(userRepository.findByUsername("John1234"));
        userRepository.delete(userRepository.findByUsername("John1234"));
    }

    @Test
    void capturesUserId() {
        try {
            userRepository.delete(userRepository.findByUsername("John1234"));
        } catch (Exception ignored) {} // Delete user if exists
        int userId = recipesService.createUser(new UserInfo("John1234", "Doe", "john@doe.com"));
        userRepository.delete(userRepository.findByUsername("John1234"));
        Assertions.assertNotEquals(0, userId);
    }

    @Test
    void deletesWithUserId() {
        try {
            userRepository.delete(userRepository.findByUsername("John1234"));
        } catch (Exception ignored) {} // Delete user if exists
        int userId = recipesService.createUser(new UserInfo("John1234", "Doe", "john@doe.com"));
        Assertions.assertDoesNotThrow(() -> userRepository.deleteById(userId));
        Assertions.assertThrows( ResourceNotFoundException.class, () -> recipesService.getUser(userId));
    }

}
