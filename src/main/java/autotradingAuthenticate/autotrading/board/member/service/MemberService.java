package autotradingAuthenticate.autotrading.board.member.service;

import autotradingAuthenticate.autotrading.board.member.dto.MemberSignupDto;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.entity.Role;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberSignupDto signupDto) {
        Member member = new Member(signupDto.getUsername(),passwordEncoder.encode(signupDto.getPassword()),signupDto.getEmail(), Role.ROLE_USER);
        memberRepository.save(member);
    }
}