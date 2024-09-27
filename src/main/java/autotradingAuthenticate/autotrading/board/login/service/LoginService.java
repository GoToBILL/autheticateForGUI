package autotradingAuthenticate.autotrading.board.login.service;

import autotradingAuthenticate.autotrading.board.jwt.JwtUtil;
import autotradingAuthenticate.autotrading.board.member.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String login(String username, String password) throws AuthenticationException {
        // 1. 사용자 인증 (사용자 존재 여부 확인 포함)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. 인증이 성공하면 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. JWT 토큰 생성
        return jwtUtil.generateToken(username);
    }
}