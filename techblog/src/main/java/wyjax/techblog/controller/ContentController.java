package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sun.applet.Main;
import wyjax.techblog.model.Content;
import wyjax.techblog.service.ContentService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private Logger logger= LoggerFactory.getLogger(Main.class);

    @GetMapping("blog/board")
    public String contentList(@PageableDefault Pageable pageable, Model model) {
        Page<Content> contents = contentService.getBoardList(pageable);
        model.addAttribute("contentList", contents);

        return "contents/boardList";
    }

    @GetMapping("blog/new")
    public String contentWrite(Model model) {
        model.addAttribute("content", new ContentForm());

        return "contents/writePage";
    }

    @PostMapping("blog/new")
    public String newWrite(ContentForm contentForm) {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();

        Content content = new Content();
        content.setTitle(contentForm.getTitle());
        content.setContent(contentForm.getContent());
        content.setUid(uid);
        content.setRegdate(LocalDateTime.now());
        content.setViewcount(0);
        content.setIsdelete('N');
        contentService.save(content);

        return "redirect:/blog/board";
    }

    @GetMapping("blog/board/{id}")
    public String viewContent(@PathVariable("id") Long id, Model model) {
        Content cont = contentService.view(id);

        if (cont != null) {
            contentService.countUp(id);
            model.addAttribute("content", cont);
            return "contents/viewPage";
        }

        return "redirect:/blog/board";
    }

    @GetMapping("blog/delete/{id}")
    public String delContent(@PathVariable Long id) {
        if (contentService.userCheck(id)) {
            contentService.delete(id);
        }

        return "redirect:/blog/board";
    }

    @GetMapping("blog/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        if (contentService.userCheck(id)) {
            Content content = contentService.view(id);
            ContentForm form = ContentForm.builder()
                    .title(content.getTitle())
                    .content(content.getContent())
                    .build();

            model.addAttribute("id", id);
            model.addAttribute("content", form);
            return "contents/editPage";
        }
        return "redirect:/blog/board";
    }

    @PostMapping("blog/edit/{id}")
    public String editContent(@PathVariable Long id, ContentForm form) {
        Content cnt = contentService.view(id);
        cnt.setTitle(form.getTitle());
        cnt.setContent(form.getContent());

        contentService.save(cnt);

        return "redirect:/blog/board/{id}";
    }
}