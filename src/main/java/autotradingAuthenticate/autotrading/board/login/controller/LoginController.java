package autotradingAuthenticate.autotrading.board.login.controller;

import autotradingAuthenticate.autotrading.board.login.service.LoginService;
import autotradingAuthenticate.autotrading.board.member.service.MyUserDetailsService;
import autotradingAuthenticate.autotrading.board.security.AuthenticationRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MyUserDetailsService myUserDetailsService;

    @GetMapping
    public String loginPage(@RequestParam(value = "redirectUrl", required = false) String redirectUrl, Model model) {
        // redirectUrl이 존재하면 모델에 추가하여 뷰에 전달
        if (redirectUrl != null) {
            model.addAttribute("redirectUrl", redirectUrl);
        }
        return "login";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest,
                                        @RequestParam(value = "redirectUrl",defaultValue = "/") String redirectUrl,
                                        HttpServletResponse response) throws Exception {
        try {
            // LoginService를 사용하여 인증 및 토큰 발급
            String jwt = loginService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            // JWT 토큰을 헤더에 추가 (필요한 경우)
            response.addHeader("Authorization", "Bearer " + jwt);

            // 토큰 반환
            return ResponseEntity.ok(jwt);
//            return "redirect:" + redirectUrl;

        } catch (AuthenticationException e) {
            // 인증 실패 시 에러 메시지와 상태 코드 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이름이나 비밀번호가 부정확합니다.");
//            return "redirect:/login?error";
        }
    }
}