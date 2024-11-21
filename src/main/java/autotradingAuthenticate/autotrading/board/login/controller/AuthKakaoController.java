package autotradingAuthenticate.autotrading.board.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthKakaoController {

    @GetMapping("/auth/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok("kakao login");
    }

}