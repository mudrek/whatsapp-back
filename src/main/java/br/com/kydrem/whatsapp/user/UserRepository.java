package br.com.kydrem.whatsapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    @Query("SELECT user FROM User user WHERE user.username LIKE %:username%")
    Optional<List<User>> findUsersByUsername(@Param("username") String username);
}
