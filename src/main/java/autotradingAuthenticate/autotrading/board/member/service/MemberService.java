package autotradingAuthenticate.autotrading.board.member.service;

import autotradingAuthenticate.autotrading.board.member.dto.MemberSignupDto;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.entity.Role;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import autotradingAuthenticate.autotrading.exception.customException.NotFoundException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberSignupDto signupDto) {
        Member member = new Member(signupDto.getUsername(),passwordEncoder.encode(signupDto.getPassword()),signupDto.getEmail(), Role.ROLE_USER);
        memberRepository.save(member);
    }
    // 사용자 이름으로 멤버 조회
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_EXIST));
    }

    // 카카오 ID로 멤버 조회
    public Member findByKakaoId(String kakaoId) {
        return memberRepository.findByKakaoId(kakaoId)
                .orElse(null); // Optional 대신 null 반환
    }

    // 새로운 멤버 저장
    public Member saveNewMember(String kakaoId, String email, String nickname, String profileImageUrl) {
        Member newMember = new Member(
                kakaoId,
                email,
                nickname,
                profileImageUrl,
                Role.ROLE_USER // 기본 역할 설정
        );
        return memberRepository.save(newMember);
    }
}