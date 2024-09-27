package autotradingAuthenticate.autotrading.board.member.service;

import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;  // MemberRepository 사용

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);  // Member를 찾음

        if (memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("Member not found with username: " + username);
        }

        Member member = memberOptional.get();

        // Role enum 값을 SimpleGrantedAuthority로 변환하여 사용자 권한 설정
        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()))  // Role Enum 사용
        );
    }
}