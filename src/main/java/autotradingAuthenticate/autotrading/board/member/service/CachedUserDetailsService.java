package autotradingAuthenticate.autotrading.board.member.service;


import autotradingAuthenticate.autotrading.board.member.dto.CustomUserDetails;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
import autotradingAuthenticate.autotrading.exception.customException.NotFoundException;
import autotradingAuthenticate.autotrading.exception.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CachedUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Cacheable(value = "userDetailsCache", key = "#p0")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 캐시 로직
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_EXIST));

        return CustomUserDetails.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(List.of(member.getRole().name()))
                .build();
    }
}