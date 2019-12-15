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
@Transactional(readOnly = true)
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
        Member members = memberRepository.findMember(member.getEmail());

        if (members != null) {
            throw new IllegalStateException("Already Regist Email");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}