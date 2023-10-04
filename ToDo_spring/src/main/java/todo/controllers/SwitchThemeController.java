package todo.controllers;


import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwitchThemeController {


    @GetMapping("/switch")
    public String switchTheme(Cookie cookie){

        return null;
    }
}
