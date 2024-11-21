package autotradingAuthenticate.autotrading.board.login.service;

import autotradingAuthenticate.autotrading.board.jwt.JwtUtil;
import autotradingAuthenticate.autotrading.board.jwt.TokenResponseDto;
import autotradingAuthenticate.autotrading.board.jwt.service.RefreshTokenService;
import autotradingAuthenticate.autotrading.board.login.KakaoUtils.KakaoUtil;
import autotradingAuthenticate.autotrading.board.login.dto.KakaoTokenResponse;
import autotradingAuthenticate.autotrading.board.login.dto.KakaoUserResponse;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.entity.Role;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthServiceImpl implements KakaoAuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public TokenResponseDto loginWithKakao(String authorizationCode) {
        // Step 1: 카카오에서 Access Token 가져오기
        KakaoTokenResponse tokenResponse = kakaoUtil.getAccessToken(authorizationCode);

        // Step 2: Access Token으로 사용자 정보 가져오기
        KakaoUserResponse userInfo = kakaoUtil.getUserInfo(tokenResponse.getAccessToken());

        // Step 3: 사용자 정보 데이터베이스 처리
        Member member = memberRepository.findByKakaoId(String.valueOf(userInfo.getId()))
                .orElseGet(() -> {
                    // 새 카카오 사용자 등록
                    Member newMember = new Member(
                            String.valueOf(userInfo.getId()),
                            userInfo.getKakaoAccount().getEmail(),
                            userInfo.getKakaoAccount().getProfile().getNickname(),
                            userInfo.getKakaoAccount().getProfile().getProfileImageUrl(),
                            Role.ROLE_USER // 기본 역할 설정
                    );
                    return memberRepository.save(newMember);
                });

        // Step 4: JWT 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(member.getEmail());
        String refreshToken = refreshTokenService.generateAndStoreRefreshToken(member.getNickname());

        return new TokenResponseDto(accessToken, refreshToken);
    }
}