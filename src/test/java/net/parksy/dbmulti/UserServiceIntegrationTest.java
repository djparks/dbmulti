package net.parksy.dbmulti;

import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetUsersWithIdGreaterThanZero() {
        // Given
        User user = User.builder()
                .username("testuser")
                .email("testuser@example.com")
                .build();
        userRepository.save(user);

        // When
        List<User> users = userService.getUsersWithIdGreaterThanZero();

        // Then
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getId()).isGreaterThan(0);
    }

    @Test
    public void testAddUser() {
        // Given
        UserDto userDto = UserDto.builder()
                .username("newuser")
                .email("newuser@example.com")
                .build();

        // When
        User savedUser = userService.addUser(userDto);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");

        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("newuser");
    }

    @Test
    public void testAddUserAsync() {
        // Given
        UserDto userDto = UserDto.builder()
                .username("asyncuser")
                .email("asyncuser@example.com")
                .build();

        // When
        userService.addUserAsync(userDto);

        // Then
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            User foundUser = userRepository.findUsersWithIdGreaterThanZero().stream()
                    .filter(u -> "asyncuser".equals(u.getUsername()))
                    .findFirst()
                    .orElse(null);
            assertThat(foundUser).isNotNull();
        });
    }

    @Test
    public void testAddUserWithManualThread() {
        // Given
        UserDto userDto = UserDto.builder()
                .username("manualuser")
                .email("manualuser@example.com")
                .build();

        // When
        userService.addUserWithManualThread(userDto);

        // Then
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            User foundUser = userRepository.findUsersWithIdGreaterThanZero().stream()
                    .filter(u -> "manualuser".equals(u.getUsername()))
                    .findFirst()
                    .orElse(null);
            assertThat(foundUser).isNotNull();
        });
    }
}
