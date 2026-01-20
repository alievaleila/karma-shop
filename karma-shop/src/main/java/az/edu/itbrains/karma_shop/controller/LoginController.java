package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.dto.RegisterDto;
import az.edu.itbrains.karma_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(RegisterDto registerDto, Model model) {
        boolean result = userService.registerUser(registerDto);
        if (!result) {
            model.addAttribute("registerError", "User already exists");
            return "login";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "The information you entered is incorrect.");
        }
        return "login";
    }
}
