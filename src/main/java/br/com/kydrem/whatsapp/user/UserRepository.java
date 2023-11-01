package br.com.kydrem.whatsapp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
}
