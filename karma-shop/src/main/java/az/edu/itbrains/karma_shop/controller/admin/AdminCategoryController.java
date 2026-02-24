package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.dto.category.CategoryFormDto;
import az.edu.itbrains.karma_shop.dto.category.CategoryListDto;
import az.edu.itbrains.karma_shop.model.Category;
import az.edu.itbrains.karma_shop.repository.CategoryRepository;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("currentPage", "categories");
        model.addAttribute("pageTitle", "Kateqoriyalar");

        List<CategoryListDto> categories = categoryRepository.findAll().stream()
                .map(this::toListDto)
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);

        return "admin/categories";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("currentPage", "categories");
        model.addAttribute("pageTitle", "Yeni Kateqoriya");
        model.addAttribute("category", new CategoryFormDto());    return "admin/category-form";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryFormDto categoryForm, RedirectAttributes redirectAttributes) {
        Category category = new Category();
        mapFormToCategory(categoryForm, category);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "Kateqoriya uğurla əlavə edildi!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kateqoriya tapılmadı"));

        model.addAttribute("currentPage", "categories");
        model.addAttribute("pageTitle", "Kateqoriyanı Redaktə Et");
        model.addAttribute("category", toFormDto(category));
        return "admin/category-form";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute CategoryFormDto categoryForm, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kateqoriya tapılmadı"));

        mapFormToCategory(categoryForm, category);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "Kateqoriya uğurla yeniləndi!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Kateqoriya uğurla silindi!");
        return "redirect:/admin/categories";
    }

    private CategoryListDto toListDto(Category category) {
        CategoryListDto dto = new CategoryListDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setProductCount(category.getProducts() != null ? category.getProducts().size() : 0);
        return dto;
    }

    private CategoryFormDto toFormDto(Category category) {
        CategoryFormDto dto = new CategoryFormDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    private void mapFormToCategory(CategoryFormDto form, Category category) {
        category.setName(form.getName());
    }
}
