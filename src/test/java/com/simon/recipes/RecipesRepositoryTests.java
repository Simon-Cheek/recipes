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
class RecipesRepositoryTests {

	private UserRepository userRepository;

	@Autowired
	RecipesRepositoryTests(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// REPOSITORY TESTS
	@Test
	void createsAUser() {
		userRepository.save(new User("John", "Doe", "john@doe.com"));
		Assertions.assertNotNull(userRepository.findByUsername("John"));
		userRepository.delete(userRepository.findByUsername("John"));
	}

}
