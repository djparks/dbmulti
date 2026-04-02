package net.parksy.dbmulti;

import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private final UserRepository userRepository;

    @Transactional
    public List<User> getUsersWithIdGreaterThanZero() {
        return userRepository.findUsersWithIdGreaterThanZero();
    }

    @Transactional
    public User addUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
        return userRepository.save(user);
    }

    @Async
    @Transactional
    public void addUserAsync(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
         userRepository.save(user);
    }

    public void addUserWithManualThread(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();

         new Thread(() -> userRepository.save(user)).start();
    }

}
