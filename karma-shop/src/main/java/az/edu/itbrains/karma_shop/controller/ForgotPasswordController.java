package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final PasswordResetTokenService passwordResetService;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgot(@RequestParam String username, Model model) {

        String token = passwordResetService.createTokenForUser(username);

        model.addAttribute("msg", "If the user exists, the reset link has been sent.");

        if (token != null) {
            System.out.println("RESET LINK: http://localhost:8080/reset-password?token=" + token);
        }

        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPage(@RequestParam String token, Model model) {
        if (!passwordResetService.isTokenValid(token)) {
            model.addAttribute("error", "The token is invalid or has expired.");
            model.addAttribute("token", token);
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handleReset(@RequestParam String token,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "The passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        boolean ok = passwordResetService.resetPassword(token, password);
        if (!ok) {
            model.addAttribute("error", "The token is invalid or has expired.");
            return "reset-password";
        }

        return "redirect:/login?resetSuccess";
    }
}
