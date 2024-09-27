package autotradingAuthenticate.autotrading.board.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 원래 요청 URL을 가져옴
        String originalUrl = request.getRequestURI();

        // 쿼리 파라미터가 있을 경우 포함
        String queryString = request.getQueryString();
        if (queryString != null) {
            originalUrl += "?" + queryString;
        }

        // 로그인 페이지 URL 생성, 원래 URL을 redirectUrl 파라미터로 추가
        String loginPageUrl = "/login?redirectUrl=" + originalUrl;

        // 로그인 페이지로 리다이렉트
        response.sendRedirect(loginPageUrl);
    }
}