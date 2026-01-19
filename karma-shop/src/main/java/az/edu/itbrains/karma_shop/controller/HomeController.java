package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.dto.blog.BlogDto;
import az.edu.itbrains.karma_shop.dto.category.CategoryDto;
import az.edu.itbrains.karma_shop.dto.deal.DealDto;
import az.edu.itbrains.karma_shop.dto.product.ProductDto;
import az.edu.itbrains.karma_shop.service.BlogService;
import az.edu.itbrains.karma_shop.service.BrandService;
import az.edu.itbrains.karma_shop.service.CategoryService;
import az.edu.itbrains.karma_shop.service.ColorService;
import az.edu.itbrains.karma_shop.service.DealService;
import az.edu.itbrains.karma_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ColorService colorService;
    private final DealService dealService;
    private final BlogService blogService;

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> productDtoList = productService.getAllProducts();
        model.addAttribute("products", productDtoList);
        return "index";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        List<BlogDto> blogDtoList = blogService.getAllBlogs();
        model.addAttribute("blogs", blogDtoList);
        return "blog";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/category")
    public String category(
            @RequestParam(value = "id", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            Model model
    ) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDtoList);

        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("colorId", colorId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        List<ProductDto> productDtoList = productService.filterTop6(categoryId, brandId, colorId, minPrice, maxPrice);

        model.addAttribute("products", productDtoList);

        List<DealDto> dealDtoList = dealService.getAllDeals();
        model.addAttribute("deals", dealDtoList);
        return "category";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/elements")
    public String elements() {
        return "elements";
    }

    @GetMapping("/single-blog")
    public String singleBlog() {
        return "single-blog";
    }

    @GetMapping("/single-product")
    public String singleProduct() {
        return "single-product";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getById(id);
        model.addAttribute("product", productDto);
        System.out.println("PRODUCT -> " + productDto.getId() + " | " + productDto.getName());
        return "single-product";
    }

    @GetMapping("/tracking")
    public String tracking() {
        return "tracking";
    }
}
