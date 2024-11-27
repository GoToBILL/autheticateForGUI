package autotradingAuthenticate.autotrading.board.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheTestService {

    // 캐시에 데이터를 저장하고 조회하는 메서드
    @Cacheable(value = "testCache", key = "#p0", unless = "#result == null")
    public String getCachedData(String key) {
        System.out.println("DB에서 데이터를 조회: " + key);
        // DB를 흉내내기 위해 일부러 지연을 추가
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        return "value-for-" + key;
    }
}
