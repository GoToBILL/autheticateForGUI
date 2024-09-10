package autotradingAuthenticate.autotrading.auth.Controller;

import autotradingAuthenticate.autotrading.auth.authDto.AuthRequest;
import autotradingAuthenticate.autotrading.auth.authDto.AuthResponse;
import autotradingAuthenticate.autotrading.auth.entity.User;
import autotradingAuthenticate.autotrading.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();  // 클라이언트 IP 주소

        // 데이터베이스에 사용자가 등록되어 있는지 확인
        if (!userService.findByUsername(authRequest.getUsername())) {
            // 등록되지 않은 사용자일 경우 접근 불가
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthResponse("User not registered"));
        }

        // 기존 사용자라면 토큰과 IP를 확인
        if (userService.validateUser(authRequest.getAuthToken(), clientIp)) {
            return ResponseEntity.ok(new AuthResponse("success"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("failure"));
    }
}
