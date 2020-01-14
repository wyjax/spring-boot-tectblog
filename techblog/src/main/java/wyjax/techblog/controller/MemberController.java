package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import wyjax.techblog.model.Member;
import wyjax.techblog.model.MemberRole;
import wyjax.techblog.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);

    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private static String authorizationRequestBaseUri = "/oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    // OAuth2 사용자 정보
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

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
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

        logger.info(oauth2AuthenticationUrls.toString());
        model.addAttribute("urls", oauth2AuthenticationUrls);

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

    @GetMapping("/user/oauth")
    public String oauthLogin() {
        return "redirect:/oauth2/authorization/github";
    }

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken auth) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());
        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            model.addAttribute("name", userAttributes.get("name"));
        }
        return "redirect:/";
    }

    @GetMapping("/user/mypage/{email}")
    public String mypage(@PathVariable("email") String email, Model model) {
        Member member = memberService.getMember(email);

        if (member.getId() == null) return "redirect:/";

        model.addAttribute("user", member);
        return "members/myPage";
    }
}