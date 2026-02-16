package az.edu.itbrains.karma_shop.controller.admin;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;
import az.edu.itbrains.karma_shop.dto.product.ProductFormDto;
import az.edu.itbrains.karma_shop.model.Brand;
import az.edu.itbrains.karma_shop.model.Category;
import az.edu.itbrains.karma_shop.model.Color;
import az.edu.itbrains.karma_shop.model.Product;
import az.edu.itbrains.karma_shop.repository.BrandRepository;
import az.edu.itbrains.karma_shop.repository.CategoryRepository;
import az.edu.itbrains.karma_shop.repository.ColorRepository;
import az.edu.itbrains.karma_shop.repository.ProductRepository;
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
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("currentPage", "products");
        model.addAttribute("pageTitle", "Məhsullar");

        List<ProductDto> products = productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        model.addAttribute("products", products);

        return "admin/products";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("currentPage", "products");
        model.addAttribute("pageTitle", "Yeni Məhsul");
        model.addAttribute("product", new ProductFormDto());
        addFormAttributes(model);
        return "admin/product-form";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductFormDto productForm, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        mapFormToProduct(productForm, product);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successMessage", "Məhsul uğurla əlavə edildi!");
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Məhsul tapılmadı"));

        model.addAttribute("currentPage", "products");
        model.addAttribute("pageTitle", "Məhsulu Redaktə Et");
        model.addAttribute("product", toFormDto(product));
        addFormAttributes(model);
        return "admin/product-form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute ProductFormDto productForm, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Məhsul tapılmadı"));

        mapFormToProduct(productForm, product);
        productRepository.save(product);
        redirectAttributes.addFlashAttribute("successMessage", "Məhsul uğurla yeniləndi!");
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Məhsul uğurla silindi!");
        return "redirect:/admin/products";
    }

    private void addFormAttributes(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("colors", colorRepository.findAll());
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }

    private ProductFormDto toFormDto(Product product) {
        ProductFormDto dto = new ProductFormDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        if (product.getBrand() != null) {
            dto.setBrandId(product.getBrand().getId());
        }
        if (product.getColor() != null) {
            dto.setColorId(product.getColor().getId());
        }
        return dto;
    }

    private void mapFormToProduct(ProductFormDto form, Product product) {
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setDiscountPrice(form.getDiscountPrice());
        product.setImageUrl(form.getImageUrl());
        product.setDescription(form.getDescription());

        if (form.getCategoryId() != null) {
            Category category = categoryRepository.findById(form.getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        if (form.getBrandId() != null) {
            Brand brand = brandRepository.findById(form.getBrandId()).orElse(null);
            product.setBrand(brand);
        }
        if (form.getColorId() != null) {
            Color color = colorRepository.findById(form.getColorId()).orElse(null);
            product.setColor(color);
        }
    }
}
