package wyjax.techblog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wyjax.techblog.model.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Member findEmailAndPassword(String email, String password) {
        return em.createQuery(
                "select m " +
                        "from Member  m " +
                        "where m.email = :email and m.pw = :password", Member.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
}