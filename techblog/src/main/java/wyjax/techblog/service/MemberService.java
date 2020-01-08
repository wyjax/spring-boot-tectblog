package wyjax.techblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wyjax.techblog.model.Member;
import wyjax.techblog.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Member members = memberRepository.findByEmail(member.getEmail());

        if (members != null) {
            throw new IllegalStateException("Already Checked Email");
        }
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = null;
        try {
            member = memberRepository.findEmailAndPassword(email);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new UserDetailsImpl(member.getEmail(), member.getPw(), member.getRoles().toString());
    }
}