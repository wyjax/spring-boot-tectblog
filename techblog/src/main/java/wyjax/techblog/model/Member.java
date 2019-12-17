package wyjax.techblog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id", unique = true)
    private Long id;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private String pw;

    // 사용자 권한
    @Column(nullable = false)
    private MemberRole roles;

    private LocalDateTime regdate;
}
