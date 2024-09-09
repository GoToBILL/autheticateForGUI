package autotradingAuthenticate.autotrading.auth.repository;

import autotradingAuthenticate.autotrading.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthToken(String authToken);
    Optional<User> findByUsername(String username);
}