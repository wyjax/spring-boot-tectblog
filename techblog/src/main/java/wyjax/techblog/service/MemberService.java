package wyjax.techblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wyjax.techblog.model.Member;
import wyjax.techblog.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
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

    public Member authenticate(String email, String password) throws Exception {
        Member member = memberRepository.findEmailAndPassword(email, password);
        return member;
    }

    // Email 중복 확인
    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByEmail(member.getEmail());

        if (!members.isEmpty()) {
            throw new IllegalStateException("Already Regist Email");
        }
    }
}