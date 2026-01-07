package az.edu.itbrains.karma_shop.controller;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;
import az.edu.itbrains.karma_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> productDtoList = productService.getAllProducts();
        model.addAttribute("products", productDtoList);
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/category")
    public String category() {
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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/single-blog")
    public String singleBlog() {
        return "single-blog";
    }

    @GetMapping("/single-product")
    public String singleProduct() {
        return "single-product";
    }

    @GetMapping("/tracking")
    public String tracking() {
        return "tracking";
    }
}
