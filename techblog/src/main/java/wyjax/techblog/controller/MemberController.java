package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import wyjax.techblog.model.Member;
import wyjax.techblog.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/join")
    public String createForm(Model model) {
        return "members/joinForm";
    }

    @PostMapping("/user/join")
    public String create(Member member) {
        return "redirect:/user/login";
    }

    @GetMapping("/user/login")
    public String loginForm(Model model) {
        return "members/loginForm";
    }

    @PostMapping("/user/login")
    public String login(Model model) {
        return "redirect:/";
    }
}