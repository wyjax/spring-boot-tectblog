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

    public Member findMember(String email) {
        String jpql = "select m from Member m where m.email = :email";
        return em.createQuery(jpql, Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}