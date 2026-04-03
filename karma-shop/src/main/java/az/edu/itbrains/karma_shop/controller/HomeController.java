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
        List<ProductDto> productDtoList = productService.getLatestProducts();
        model.addAttribute("products", productDtoList);

        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDtoList);

        List<DealDto> dealDtoList = dealService.getAllDeals();
        model.addAttribute("deals", dealDtoList);

        return "index";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        List<BlogDto> blogDtoList = blogService.getAllBlogs();
        model.addAttribute("blogs", blogDtoList);
        return "blog";
    }

    @GetMapping("/category")
    public String category(
            @RequestParam(value = "id", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sortBy", required = false, defaultValue = "newest") String sortBy,
            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
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
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);

        List<ProductDto> allProducts = productService.filterAll(categoryId, brandId, colorId, minPrice, maxPrice);

        if ("price_low".equals(sortBy)) {
            allProducts.sort((a, b) -> Double.compare(a.getDiscountPrice() != null ? a.getDiscountPrice() : a.getPrice(),
                    b.getDiscountPrice() != null ? b.getDiscountPrice() : b.getPrice()));
        } else if ("price_high".equals(sortBy)) {
            allProducts.sort((a, b) -> Double.compare(b.getDiscountPrice() != null ? b.getDiscountPrice() : b.getPrice(),
                    a.getDiscountPrice() != null ? a.getDiscountPrice() : a.getPrice()));
        }


        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / limit);
        
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;
        
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, totalProducts);
        
        List<ProductDto> paginatedProducts = allProducts.subList(startIndex, endIndex);

        model.addAttribute("products", paginatedProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        List<DealDto> dealDtoList = dealService.getAllDeals();
        model.addAttribute("deals", dealDtoList);
        return "category";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/elements")
    public String elements() {
        return "elements";
    }

    @GetMapping("/single-blog/{id}")
    public String singleBlog(@PathVariable Long id, Model model) {
        BlogDto blog = blogService.getById(id);
        model.addAttribute("blog", blog);
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

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortBy", required = false, defaultValue = "newest") String sortBy,
            @RequestParam(value = "limit", required = false, defaultValue = "12") Integer limit,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            Model model
    ) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryDtoList);

        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("colors", colorService.getAll());

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("limit", limit);
        model.addAttribute("page", page);

        List<ProductDto> allProducts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            allProducts = productService.searchByName(keyword);
            model.addAttribute("searchKeyword", keyword);
        } else {
            allProducts = productService.getLatestProducts();
        }

        if ("price_low".equals(sortBy)) {
            allProducts.sort((a, b) -> Double.compare(a.getDiscountPrice() != null ? a.getDiscountPrice() : a.getPrice(),
                    b.getDiscountPrice() != null ? b.getDiscountPrice() : b.getPrice()));
        } else if ("price_high".equals(sortBy)) {
            allProducts.sort((a, b) -> Double.compare(b.getDiscountPrice() != null ? b.getDiscountPrice() : b.getPrice(),
                    a.getDiscountPrice() != null ? a.getDiscountPrice() : a.getPrice()));
        }

        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / limit);
        
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;
        
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, totalProducts);
        
        List<ProductDto> paginatedProducts = allProducts.subList(startIndex, endIndex);

        model.addAttribute("products", paginatedProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        List<DealDto> dealDtoList = dealService.getAllDeals();
        model.addAttribute("deals", dealDtoList);
        return "category";
    }

}
