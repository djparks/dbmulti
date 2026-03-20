package net.parksy.dbmulti.service;

import net.parksy.dbmulti.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateAndGetUser() {
        // Given
        String name = "John Doe";
        String email = "john.doe@example.com";

        // When
        User savedUser = userService.createUser(name, email);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(name);
        assertThat(savedUser.getEmail()).isEqualTo(email);

        // And When
        Optional<User> foundUser = userService.getUserByEmail(email);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo(name);
    }

    @Test
    void testGetAllUsers() {
        // Given
        userService.createUser("Alice", "alice@example.com");
        userService.createUser("Bob", "bob@example.com");

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getName).containsExactlyInAnyOrder("Alice", "Bob");
    }
}
