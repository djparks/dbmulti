package net.parksy.dbmulti.service;

import lombok.RequiredArgsConstructor;
import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String name, String email) {
        User user = User.builder().name(name).email(email).build();
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
