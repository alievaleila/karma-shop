package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.model.Contact;
import az.edu.itbrains.karma_shop.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/contacts")
@RequiredArgsConstructor
public class AdminContactController {

    private final ContactRepository contactRepository;

    @GetMapping
    public String listContacts(Model model) {
        model.addAttribute("currentPage", "contacts");
        model.addAttribute("pageTitle", "Mesajlar");

        List<Contact> contacts = contactRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("contacts", contacts);

        return "admin/contacts";
    }

    @PostMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Mesaj uğurla silindi!");
        return "redirect:/admin/contacts";
    }
}
