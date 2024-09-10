package autotradingAuthenticate.autotrading.auth.repository;

import autotradingAuthenticate.autotrading.auth.entity.UserIp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIpRepository extends JpaRepository<UserIp, Long> {
}