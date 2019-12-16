package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wyjax.techblog.model.Member;
import wyjax.techblog.model.MemberRole;
import wyjax.techblog.service.MemberService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/user/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/joinForm";
    }

    @PostMapping("/user/join")
    public String create(MemberForm memberForm) {
        Member member = new Member();
        member.setEmail(memberForm.getEmail());
        member.setPw(memberForm.getPassword());
        member.setRoles(MemberRole.BASIC);
        member.setRegdate(LocalDateTime.now());
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

        return "members/loginForm";
    }

    @PostMapping("/user/login")
    public String login() {
        return "redirect:/";
    }
}