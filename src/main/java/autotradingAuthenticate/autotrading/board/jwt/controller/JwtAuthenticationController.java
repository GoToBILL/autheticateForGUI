package autotradingAuthenticate.autotrading.utils.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationService jwtAuthenticationService;


    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader("Authorization") String authHeader, HttpServletRequest request) {

        Map<String, Object> responseMap = new HashMap<>();
        try {
            // JWT 토큰 검증 및 사용자 인증 정보 설정
            jwtAuthenticationService.authenticateToken(authHeader, request, null); // response는 null로 설정
            // 토큰이 유효하면 응답에 true와 사용자 정보를 추가
            responseMap.put("valid", true);
            responseMap.put("username", jwtUtil.extractUsername(authHeader.substring(7)));
        } catch (Exception e) {
            // 예외가 발생하면 유효하지 않음을 표시
            responseMap.put("valid", false);
            responseMap.put("error", e.getMessage());
        }

        return ResponseEntity.ok(responseMap);
    }
}