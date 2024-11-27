package autotradingAuthenticate.autotrading.board.member.controller;


import autotradingAuthenticate.autotrading.board.member.service.CacheTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CacheTestController {

    private final CacheTestService cacheTestService;

    @GetMapping("/cache-test")
    public String testCache(@RequestParam("arg0") String key) {
        return cacheTestService.getCachedData(key);
    }
}