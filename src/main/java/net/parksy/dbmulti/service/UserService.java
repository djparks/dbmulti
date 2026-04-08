package net.parksy.dbmulti.service;

import jakarta.persistence.EntityManager;
import net.parksy.dbmulti.UserDto;
import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    public UserService(UserRepository userRepository, @Qualifier("entityManagerFactory") EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }
    private final UserRepository userRepository;
    private final EntityManager entityManager;

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

    @Transactional
    public void updateFirstUserToUserOne() {
        List<User> users = userRepository.findUsersWithIdGreaterThanZero();
        if (!users.isEmpty()) {
            User user = users.get(0);
            user.setUsername("userone");
            userRepository.saveAndFlush(user);
            entityManager.refresh(user);
        }
    }

}
