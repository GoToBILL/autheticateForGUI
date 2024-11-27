package autotradingAuthenticate.autotrading.board.jwt.service;

import autotradingAuthenticate.autotrading.board.member.service.CachedUserDetailsService;
import autotradingAuthenticate.autotrading.board.jwt.JwtUtil;
import autotradingAuthenticate.autotrading.exception.customException.AuthenticationException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final JwtUtil jwtUtil;
//    private final MyUserDetailsService userDetailsService;
    private final CachedUserDetailsService cachedUserDetailsService;
    /**
     * JWT 토큰을 검증하고, 유효한 경우 사용자 인증 정보를 설정한다.
     * 유효하지 않으면 리프레시 토큰을 사용하여 새 액세스 토큰을 발급한다.
     *
     * @param authorizationHeader Authorization 헤더 값
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @throws Exception JWT 토큰이 유효하지 않은 경우 예외 발생
     */
    public void authenticateToken(String authorizationHeader, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = null;
        String jwt = null;

        // Authorization 헤더에서 JWT 토큰 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                // 토큰에서 사용자 이름 추출
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // JWT 파싱 중 예외 발생 시
                throw new AuthenticationException(ErrorMessage.INVALID_TOKEN);
            }
        }

        // 사용자 이름이 있고 인증 정보가 없는 경우 JWT 검증
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDetails userDetails = cachedUserDetailsService.loadUserByUsername(username);
            // 3. 액세스 토큰 유효성 검증 및 SecurityContext에 인증 정보 설정
            if (jwtUtil.validateAccessToken(jwt, userDetails.getUsername())) {
                setAuthentication(userDetails, request); // 인증 설정
            } else {
                throw new AuthenticationException(ErrorMessage.EXPIRED_TOKEN);
            }
        }
    }

    // SecurityContext에 인증 정보 설정 메서드
    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}