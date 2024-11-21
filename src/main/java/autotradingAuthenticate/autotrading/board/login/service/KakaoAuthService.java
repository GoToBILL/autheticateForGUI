package autotradingAuthenticate.autotrading.board.login.service;

import autotradingAuthenticate.autotrading.board.jwt.TokenResponseDto;
import autotradingAuthenticate.autotrading.board.login.dto.KakaoUserResponse;

public interface KakaoAuthService {
    TokenResponseDto loginWithKakao(String authorizationCode);
}