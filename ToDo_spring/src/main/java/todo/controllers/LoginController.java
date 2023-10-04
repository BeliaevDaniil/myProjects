package todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login-error")
    public String displayError(Model model){
        model.addAttribute("errorMessage", "Username or password is wrong");
        return "login";
    }

}
