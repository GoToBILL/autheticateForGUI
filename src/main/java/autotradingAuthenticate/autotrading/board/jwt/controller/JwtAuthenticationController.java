package autotradingAuthenticate.autotrading.board.jwt.controller;

import autotradingAuthenticate.autotrading.board.jwt.service.JwtAuthenticationService;
import autotradingAuthenticate.autotrading.board.jwt.JwtUtil;
import autotradingAuthenticate.autotrading.exception.ApiResponse;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import autotradingAuthenticate.autotrading.exception.response.SuccessMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateToken(
            @RequestHeader("Authorization") String authHeader, HttpServletRequest request) {

        Map<String, Object> responseMap = new HashMap<>();
        try {
            // JWT 토큰 검증 및 사용자 인증 정보 설정
            jwtAuthenticationService.authenticateToken(authHeader, request, null); // response는 null로 설정

            // 토큰이 유효하면 응답 데이터 설정
            responseMap.put("valid", true);
            responseMap.put("username", jwtUtil.extractUsername(authHeader.substring(7)));

            return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TOKEN_VALIDATION_SUCCESS, responseMap));
        } catch (Exception e) {
            // 예외 발생 시 유효하지 않음을 표시
            responseMap.put("valid", false);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 적절한 상태 코드 설정
                    .body(ApiResponse.error(ErrorMessage.INVALID_TOKEN));
        }
    }

}