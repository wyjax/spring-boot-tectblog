package wyjax.techblog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@DynamicInsert
public class Content {
    /*
        dafault 값을 주는 방법
        1. DynamicInsert, DynamicUpdate
        2. PreInsert, PreUpdate
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", unique = true)
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("'N'")
    private char isdelete;

    @ColumnDefault("0")
    private Integer viewcount;

    private LocalDateTime regdate;
}