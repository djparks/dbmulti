package net.parksy.dbmulti.repository;

import net.parksy.dbmulti.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE id > 0")
    List<User> findUsersWithIdGreaterThanZero();

}
