package wyjax.techblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.applet.Main;

@Controller
public class HomeController {
    private Logger logger= LoggerFactory.getLogger(Main.class);

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("profile")
    public String profile() {
        return "profile";
    }
}
