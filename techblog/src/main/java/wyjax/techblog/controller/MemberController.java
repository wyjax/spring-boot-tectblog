package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wyjax.techblog.model.Member;
import wyjax.techblog.repository.MemberRepository;
import wyjax.techblog.service.MemberRole;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/user/join")
    public String createForm(Model model) {
        return "members/createMemberForm";
    }

    @PostMapping("/user/join")
    public String create(Member member) {
        MemberRole role = new MemberRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPw(passwordEncoder.encode(member.getPw()));
        role.setRoleName("BASIC");
        member.setRoles(Arrays.asList(role));
        memberRepository.save(member);

        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String loginForm(Model model) {
        return "members/loginMemberForm";
    }
}