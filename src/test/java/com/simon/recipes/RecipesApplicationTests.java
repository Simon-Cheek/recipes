package com.simon.recipes;

import com.simon.recipes.dto.UserInfo;
import com.simon.recipes.entity.User;
import com.simon.recipes.repository.UserRepository;
import com.simon.recipes.service.RecipesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipesApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RecipesServiceImpl recipesService;

	// REPOSITORY TESTS
	@Test
	void createsAUser() {
		userRepository.save(new User("John", "Doe", "john@doe.com"));
		Assertions.assertNotNull(userRepository.findByUsername("John"));
		userRepository.delete(userRepository.findByUsername("John"));
	}

	// SERVICE TESTS
	@Test
	void createsAUserFromService() {
		recipesService.createUser(new UserInfo("John", "Doe", "john@doe.com"));
		Assertions.assertNotNull(userRepository.findByUsername("John"));
		userRepository.delete(userRepository.findByUsername("John"));
	}

}
