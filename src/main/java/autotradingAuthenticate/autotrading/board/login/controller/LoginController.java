package autotradingAuthenticate.autotrading.board.login.controller;

import autotradingAuthenticate.autotrading.board.jwt.TokenResponseDto;
import autotradingAuthenticate.autotrading.board.login.service.LoginService;

import autotradingAuthenticate.autotrading.board.security.AuthenticationRequest;
import autotradingAuthenticate.autotrading.exception.ApiResponse;
import autotradingAuthenticate.autotrading.exception.customException.UnauthorizedException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import autotradingAuthenticate.autotrading.exception.response.SuccessMessage;
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
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // LoginService를 사용하여 인증 및 토큰 발급
            TokenResponseDto tokens = loginService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            // 토큰을 JSON 응답으로 반환
            return ResponseEntity.ok(ApiResponse.success(SuccessMessage.USER_LOGIN_SUCCESS,tokens));

        } catch (AuthenticationException e) {
            // 인증 실패 시 에러 메시지와 상태 코드 반환
            throw new UnauthorizedException(ErrorMessage.USER_LOGIN_FAILED);
        }
    }
}