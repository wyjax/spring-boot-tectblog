package wyjax.techblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContentController {

    @GetMapping("blog/board")
    public String contentList() {
        return "contents/boardList";
    }

    @GetMapping("blog/new")
    public String contentWrite(Model model) {
        model.addAttribute("contentForm", new contentForm());
        return "contents/writePage";
    }

    @PostMapping("blog/new")
    public String newWrite() {
        return "";
    }
}