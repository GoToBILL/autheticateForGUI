package autotradingAuthenticate.auth.Controller;

import autotradingAuthenticate.auth.authDto.AuthRequest;
import autotradingAuthenticate.auth.authDto.AuthResponse;
import autotradingAuthenticate.auth.entity.User;
import autotradingAuthenticate.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();  // 클라이언트 IP 주소

        if (userService.isNewUser(authRequest.getUsername())) {
            // 새로운 사용자라면 UUID를 생성하고 사용자 정보를 DB에 저장
            User newUser = userService.createNewUser(authRequest.getUsername(), clientIp);
            return ResponseEntity.ok(new AuthResponse("success", newUser.getAuthToken()));
        }

        // 기존 사용자라면 토큰과 IP를 확인
        if (userService.validateUser(authRequest.getAuthToken(), clientIp)) {
            return ResponseEntity.ok(new AuthResponse("success"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("failure"));
    }
}
