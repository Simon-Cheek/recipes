package com.simon.recipes;

import com.simon.recipes.dto.ItemDTO;
import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.Recipe;
import com.simon.recipes.entity.User;
import com.simon.recipes.exceptions.ResourceNotFoundException;
import com.simon.recipes.repository.UserRepository;
import com.simon.recipes.service.RecipesServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Transactional
@SpringBootTest
class RecipesServiceTests {

    private UserRepository userRepository;
    private RecipesServiceImpl recipesService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RecipesServiceTests(UserRepository userRepository, RecipesServiceImpl recipesService) {
        this.userRepository = userRepository;
        this.recipesService = recipesService;
    }

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

    @Test
    void canAddCategoryToUser() {
        try {
            userRepository.delete(userRepository.findByUsername("John1"));
        } catch (Exception ignored) {} // Delete user if exists
        int userId = recipesService.createUser(new UserInfo("John1", "Doe", "john@doe.com"));
        ItemDTO newCategory = new ItemDTO(String.valueOf(userId), "New Category", "Test Category555", null);
        recipesService.createCategory(newCategory);

        // Flush and clear session to make sure the changes are reflected when using @Transactional
        entityManager.flush();
        entityManager.clear();

        User fullUser = recipesService.getFullUser(userId);

        Assertions.assertNotNull(fullUser.getCategories());
        Assertions.assertEquals(1, fullUser.getCategories().size());
    }

    @Test
    void canAddRecipeToUser() {
        try {
            userRepository.delete(userRepository.findByUsername("John1"));
        } catch (Exception ignored) {} // Delete user if exists
        int userId = recipesService.createUser(new UserInfo("John1", "Doe", "john@doe.com"));
        ItemDTO newRecipe = new ItemDTO(String.valueOf(userId), "New Recipe", "Test Recipe55", null);
        recipesService.createRecipe(newRecipe);

        // Flush and clear session to make sure the changes are reflected when using @Transactional
        entityManager.flush();
        entityManager.clear();

        User fullUser = recipesService.getFullUser(userId);

        Assertions.assertNotNull(fullUser.getRecipes());
        Assertions.assertEquals(1, fullUser.getRecipes().size());
    }

    @Test
    void canRemoveRecipeFromUser() {
        try {
            userRepository.delete(userRepository.findByUsername("John1"));
        } catch (Exception ignored) {} // Delete user if exists
        int userId = recipesService.createUser(new UserInfo("John1", "Doe", "john@doe.com"));
        ItemDTO newRecipe = new ItemDTO(String.valueOf(userId), "New Recipe", "Test Recipe55", null);
        int recipeID = recipesService.createRecipe(newRecipe);

        // Flush and clear session to make sure the changes are reflected when using @Transactional
        entityManager.flush();
        entityManager.clear();

        User fullUser = recipesService.getFullUser(userId);

        Assertions.assertNotNull(fullUser.getRecipes());
        Assertions.assertEquals(1, fullUser.getRecipes().size());

        // Delete and make sure User no longer has recipe
        recipesService.deleteRecipe(recipeID);

        User fullUser2 = recipesService.getFullUser(userId);
        // Make sure Recipe Deletion doesn't delete a user
        Assertions.assertNotNull(fullUser2);

        Assertions.assertNotNull(fullUser2.getRecipes());
        Assertions.assertEquals(0, fullUser2.getRecipes().size());
    }

}
