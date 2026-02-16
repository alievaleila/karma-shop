package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("currentPage", "users");
        model.addAttribute("pageTitle", "İstifadəçilər");

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/users";
    }

    @GetMapping("/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        model.addAttribute("currentPage", "users");
        model.addAttribute("pageTitle", "İstifadəçi: " + user.getUsername());
        model.addAttribute("user", user);

        return "admin/user-detail";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        // Admin istifadəçisini silməyə icazə vermə
        if (user.getUsername().toLowerCase().contains("admin")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Admin istifadəçisini silmək olmaz!");
            return "redirect:/admin/users";
        }

        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "İstifadəçi uğurla silindi!");
        return "redirect:/admin/users";
    }
}
