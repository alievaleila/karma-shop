package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.dto.brand.BrandFormDto;
import az.edu.itbrains.karma_shop.model.Brand;
import az.edu.itbrains.karma_shop.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {

    private final BrandRepository brandRepository;

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("currentPage", "brands");
        model.addAttribute("pageTitle", "Brendlər");

        List<Brand> brands = brandRepository.findAll();
        model.addAttribute("brands", brands);

        return "admin/brands";
    }

    @GetMapping("/add")
    public String addBrandForm(Model model) {
        model.addAttribute("currentPage", "brands");
        model.addAttribute("pageTitle", "Yeni Brend");
        model.addAttribute("brand", new BrandFormDto());
        return "admin/brand-form";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute BrandFormDto brandForm, RedirectAttributes redirectAttributes) {
        Brand brand = new Brand();
        brand.setName(brandForm.getName());
        brandRepository.save(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brend uğurla əlavə edildi!");
        return "redirect:/admin/brands";
    }

    @GetMapping("/edit/{id}")
    public String editBrandForm(@PathVariable Long id, Model model) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brend tapılmadı"));

        model.addAttribute("currentPage", "brands");
        model.addAttribute("pageTitle", "Brendi Redaktə Et");

        BrandFormDto dto = new BrandFormDto();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        model.addAttribute("brand", dto);

        return "admin/brand-form";
    }

    @PostMapping("/edit/{id}")
    public String editBrand(@PathVariable Long id, @ModelAttribute BrandFormDto brandForm, RedirectAttributes redirectAttributes) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brend tapılmadı"));

        brand.setName(brandForm.getName());
        brandRepository.save(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brend uğurla yeniləndi!");
        return "redirect:/admin/brands";
    }

    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        brandRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Brend uğurla silindi!");
        return "redirect:/admin/brands";
    }
}
