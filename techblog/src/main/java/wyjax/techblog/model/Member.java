package wyjax.techblog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String pw;

    // 사용자 권한
    @Column(nullable = false)
    private MemberRole roles;

    @CreationTimestamp
    private Date regdate;

    @UpdateTimestamp
    private Date update;
}
