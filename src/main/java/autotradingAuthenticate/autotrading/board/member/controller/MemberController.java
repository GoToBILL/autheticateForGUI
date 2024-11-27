package autotradingAuthenticate.autotrading.board.member.controller;

import autotradingAuthenticate.autotrading.board.member.dto.MemberSignupDto;
import autotradingAuthenticate.autotrading.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/signup")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String signupForm(@RequestParam(value = "redirectUrl", required = false) String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);

        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute MemberSignupDto signupDto, @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
        memberService.signup(signupDto); // 회원가입 로직 수행

        return "redirect:/";
//        // 로그인 페이지로 리다이렉트, redirectUrl 포함
//        if (redirectUrl != null && !redirectUrl.isEmpty()) {
//            return "redirect:/login?redirectUrl=" + redirectUrl;
//        } else {
//            return "redirect:/login";
//        }
    }
}