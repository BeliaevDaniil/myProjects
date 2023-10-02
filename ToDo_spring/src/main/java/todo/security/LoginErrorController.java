package todo.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginErrorController {

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("errorMessage", "Username or password is wrong");
        return "login";
    }

}
