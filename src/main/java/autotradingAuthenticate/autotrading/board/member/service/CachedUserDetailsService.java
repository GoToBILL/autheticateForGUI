package autotradingAuthenticate.autotrading.board.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class CachedUserDetailsService {

    private final MyUserDetailsService userDetailsService;

    @Cacheable(value = "userDetailsCache", key = "#root.args[0]")
    public UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

}