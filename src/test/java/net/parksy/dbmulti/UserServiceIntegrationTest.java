package net.parksy.dbmulti;

import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
}
