package todo.controllers;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import todo.data.UserRepository;
import todo.entity.RegistrationForm;
import todo.entity.User;

@Slf4j
@Controller
@RequestMapping("/register")
@Data
public class RegistrationController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm regForm, Errors errors){
        User user = regForm.toUser(passwordEncoder);
        if (isValid(regForm, errors, user)) {
            userRepo.save(user);
            return "redirect:/login";
        } else {
            return "registration";
        }
    }

    public boolean isValid(@Valid RegistrationForm regForm, Errors errors, User user){
        if (errors.hasErrors()) {
            return false;
        } if (userRepo.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "1", "Username already exists");
            return false;
        } if (!regForm.getPassword().equals(regForm.getPassword2())) {
            errors.rejectValue("password2", "2", "Passwords do not match");
            return false;
        } else {
            return true;
        }
    }
}
