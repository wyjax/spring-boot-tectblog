package wyjax.techblog.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import wyjax.techblog.service.MemberRole;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    private String pw;

    @CreationTimestamp
    private Date regdate;

    @UpdateTimestamp
    private Date updatedate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private List<MemberRole> roles;
}
