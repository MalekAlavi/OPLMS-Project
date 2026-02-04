package mk.ukim.finki.wp.db.controller;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.user.enums.Role;
import mk.ukim.finki.wp.db.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam Role role) {
        userService.registerUser(firstName, lastName, email, password, role);
        return "redirect:/login";
    }
}
