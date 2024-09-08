package autotradingAuthenticate.auth.service;

import autotradingAuthenticate.auth.entity.User;
import autotradingAuthenticate.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createNewUser(String username, String ipAddress) {
        User user = new User(username, UUID.randomUUID().toString(),ipAddress);
        return userRepository.save(user);
    }

    public Optional<User> findUserByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    public boolean validateUser(String authToken, String currentIpAddress) {
        Optional<User> userOpt = findUserByAuthToken(authToken);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // IP 주소가 일치하는지 확인
            return user.getIpAddress().equals(currentIpAddress);
        }
        return false;
    }

    public boolean isNewUser(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}