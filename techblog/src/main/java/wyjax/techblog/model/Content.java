package wyjax.techblog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "content")
public class Content {

    @Id @GeneratedValue
    @Column(name = "content_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int viewcount;

    private LocalDateTime regdate;
}