package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import wyjax.techblog.model.Content;
import wyjax.techblog.service.ContentService;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("blog/board")
    public String contentList(@PageableDefault Pageable pageable, Model model) {
        Page<Content> contents = contentService.getBoardList(pageable);
        model.addAttribute("contentList", contents);

        return "contents/boardList";
    }

    @GetMapping("blog/new")
    public String contentWrite(Model model) {
        model.addAttribute("contentForm", new ContentForm());

        return "contents/writePage";
    }

    @PostMapping("blog/new")
    public String newWrite(ContentForm contentForm) {
        String uid = SecurityContextHolder.getContext().getAuthentication().getName();
        contentService.save(contentForm.getTitle(), contentForm.getContent(), uid);

        return "redirect:/blog/board";
    }

    @GetMapping("blog/board/{id}")
    public String viewContent(@PathVariable("id") Long id, Model model) {
        Content cont = contentService.view(id);
        model.addAttribute("content", cont);

        return "contents/viewPage";
    }
}