package autotradingAuthenticate.autotrading.board.login.controller;

import autotradingAuthenticate.autotrading.board.jwt.TokenResponseDto;
import autotradingAuthenticate.autotrading.board.login.dto.KakaoUserResponse;
import autotradingAuthenticate.autotrading.board.login.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthKakaoController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/auth/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String authorizationCode) {
        // 카카오 OAuth를 통해 로그인 처리 및 JWT 토큰 생성
        TokenResponseDto token = kakaoAuthService.loginWithKakao(authorizationCode);

        // JWT 토큰 반환
        return ResponseEntity.ok(token);
    }
}