package autotradingAuthenticate.autotrading.board.jwt;

import autotradingAuthenticate.autotrading.board.member.service.CachedUserDetailsService;
import autotradingAuthenticate.autotrading.board.member.service.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;
    private final CachedUserDetailsService cachedUserDetailsService; // 변경된 부분

    /**
     * JWT 토큰을 검증하고, 유효한 경우 사용자 인증 정보를 설정한다.
     *
     * @param authorizationHeader Authorization 헤더 값
     * @param request    HttpServletRequest
     * @throws Exception JWT 토큰이 유효하지 않은 경우 예외 발생
     */
    public void authenticateToken(String authorizationHeader, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String username = null;
        String jwt = null;

        // Authorization 헤더에서 JWT 토큰을 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // JWT 토큰 파싱 중 예외 발생 시 처리
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return; // 필터 체인 중단
            }
        }

        // 사용자 인증 정보가 없을 경우, JWT 검증
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.cachedUserDetailsService.loadUserByUsername(username);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 토큰이 유효한 경우, 사용자 인증을 설정
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                // JWT 토큰이 유효하지 않은 경우
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return; // 필터 체인 중단
            }
        }
    }
}
