package wyjax.techblog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Content {

    @Id @GeneratedValue
    @Column(name = "content_id")
    private Long id;

    private String title;

    private String content;
}
