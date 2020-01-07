package wyjax.techblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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

        if (cont != null) {
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

        return "redirect:/contents/boardList";
    }

    @GetMapping("blog/edit/{id}")
    public String editContent(@PathVariable Long id, Model model) {
        if (contentService.userCheck(id)) {
            Content content = contentService.view(id);
            ContentForm form = new ContentForm();
            form.setTitle(content.getTitle());
            form.setContent(content.getContent());
            model.addAttribute("content", form);
            return "contents/editPage";
        }
        return "redirect:/blog/board";
    }
}