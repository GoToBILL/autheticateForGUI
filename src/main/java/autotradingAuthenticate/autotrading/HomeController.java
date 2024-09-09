package autotradingAuthenticate.autotrading;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Thymeleaf 템플릿 파일명 (index.html)
    }
}