package autotradingAuthenticate.autotrading.board.security;

import autotradingAuthenticate.autotrading.board.jwt.service.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            // JwtAuthenticationService를 사용하여 토큰 검증 및 인증 처리
            jwtAuthenticationService.authenticateToken(authorizationHeader, request, response);
        } catch (Exception e) {
            // 예외 발생 시 401 응답 전송 및 필터 체인 중단
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        // 다음 필터 또는 요청 처리를 계속 진행
        chain.doFilter(request, response);
    }
}
