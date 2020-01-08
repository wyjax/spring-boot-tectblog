package wyjax.techblog.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentForm {
    private String title;
    private String content;

    @Builder
    public ContentForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ContentForm() {

    }
}