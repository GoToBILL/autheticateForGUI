package autotradingAuthenticate.autotrading.board.member.service;


import autotradingAuthenticate.autotrading.board.member.dto.CustomUserDetails;
import autotradingAuthenticate.autotrading.board.member.entity.Member;
import autotradingAuthenticate.autotrading.board.member.repository.MemberRepository;
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 1. 캐시에서 조회
//        CustomUserDetails cachedUser = getCachedUserDetails(username);
//        if (cachedUser != null) {
//            return cachedUser;
//        }
//
//        // 2. DB에서 사용자 조회
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        // 3. CustomUserDetails 생성
//        CustomUserDetails userDetails = CustomUserDetails.builder()
//                .username(member.getUsername())
//                .password(member.getPassword())
//                .roles(List.of(member.getRole().name()))
//                .build();
//
//        // 4. 캐시에 저장
//        cacheUserDetails(username, userDetails);
//
//        return userDetails;
//    }
//
//    private CustomUserDetails getCachedUserDetails(String username) {
//        return cacheManager.getCache("userDetailsCache") != null ?
//                cacheManager.getCache("userDetailsCache").get(username, CustomUserDetails.class) : null;
//    }
//
//    private void cacheUserDetails(String username, CustomUserDetails userDetails) {
//        if (cacheManager.getCache("userDetailsCache") != null) {
//            cacheManager.getCache("userDetailsCache").put(username, userDetails);
//
//            CustomUserDetails cachedData = cacheManager.getCache("userDetailsCache").get(username, CustomUserDetails.class);
//            if (cachedData != null) {
//                System.out.println("Cached data retrieved: " + cachedData.getPassword());
//            } else {
//                System.out.printpackage autotradingAuthenticate.autotrading.board.member.service;
//
//

    @Cacheable(value = "userDetailsCache", key = "#p0")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 캐시 로직
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return CustomUserDetails.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(List.of(member.getRole().name()))
                .build();
    }
}