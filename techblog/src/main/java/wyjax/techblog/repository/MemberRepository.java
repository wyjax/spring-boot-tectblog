package wyjax.techblog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wyjax.techblog.model.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Member findEmailAndPassword(String email) throws Exception {
        Member member = null;

        try {
            member = (Member) em.createQuery("select m from Member m where m.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }
}