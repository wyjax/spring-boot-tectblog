package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wyjax.techblog.model.Member;
import wyjax.techblog.model.MemberRole;
import wyjax.techblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = new Member();
        member.setEmail(memberForm.getEmail());
        member.setPw(passwordEncoder.encode(memberForm.getPassword()));
        member.setRoles(MemberRole.BASIC);
        member.setRegdate(LocalDateTime.now());

        try {
            memberService.join(member);
        }
        catch (Exception e) {
            return "redirect:/user/join";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/user/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "members/loginForm";
    }

    @PostMapping("/user/login")
    public String login() {
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/user/login";
    }

    @GetMapping("/user/mypage/{email}")
    public String mypage(@PathVariable("email") String email, Model model) {
        Member member = memberService.getMember(email);

        if (member.getId() == null) {
            return "redirect:/";
        }

        model.addAttribute("user", member);
        return "members/myPage";
    }
}