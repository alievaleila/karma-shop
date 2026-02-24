package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> getLatestProducts();

    List<ProductDto> getProductsByCategoryId(Long categoryId);

    ProductDto getById(Long id);

    List<ProductDto> filterTop6(Long categoryId, Long brandId, Long colorId, Double minPrice, Double maxPrice);
}
