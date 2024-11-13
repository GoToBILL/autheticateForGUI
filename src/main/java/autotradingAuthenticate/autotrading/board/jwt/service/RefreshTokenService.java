package autotradingAuthenticate.autotrading.board.jwt.service;

import autotradingAuthenticate.autotrading.board.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    private final JwtUtil jwtUtil;

    // 리프레시 토큰 생성 및 저장
    public String generateAndStoreRefreshToken(String username) {
        String refreshToken = jwtUtil.createRefreshToken(username);
        redisTemplate.opsForValue().set("refreshToken:" + username, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
        return refreshToken;
    }

    // 리프레시 토큰 가져오기
    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get("refreshToken:" + username);
    }

    // 리프레시 토큰 유효성 검증
//    public Boolean validateRefreshToken(String token) {
//        return jwtUtil.validateRefreshToken(token);
//    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(String username) {
        redisTemplate.delete("refreshToken:" + username);
    }
}