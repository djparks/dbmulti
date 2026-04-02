package net.parksy.dbmulti;

import net.parksy.dbmulti.entity.User;
import net.parksy.dbmulti.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
