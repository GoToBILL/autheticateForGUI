package autotradingAuthenticate.autotrading.auth.service;

import autotradingAuthenticate.autotrading.auth.entity.User;
import autotradingAuthenticate.autotrading.auth.entity.UserIp;
import autotradingAuthenticate.autotrading.auth.repository.UserIpRepository;
import autotradingAuthenticate.autotrading.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserIpRepository userIpRepository; // UserIp 관련 CRUD 작업을 위한 repository 추가

    // 사용자 유효성 검사 및 IP 처리
    public boolean validateUser(String authToken, String currentIpAddress) {
        Optional<User> userOpt = findUserByAuthToken(authToken);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 사용자의 IP 목록에서 요청한 IP가 있는지 확인
            boolean ipExists = user.getIpAddresses().stream()
                    .anyMatch(userIp -> userIp.getIpAddress().equals(currentIpAddress));

            // IP가 없을 경우 -> 첫 접속, IP를 새로 추가
            if (!ipExists) {
                UserIp newUserIp = new UserIp(currentIpAddress, user);
                user.getIpAddresses().add(newUserIp);
                // 새 IP를 저장
                userIpRepository.save(newUserIp);

                return true; // 첫 접속이므로 사용자 인증 성공
            }

            // IP가 있는 경우 -> 인증 성공
            return true;
        }
        // 사용자 또는 토큰이 없으면 인증 실패
        return false;
    }

    // authToken으로 사용자 찾기
    public Optional<User> findUserByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }
    // 사용자 이름으로 존재 여부 확인
    public boolean findByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}