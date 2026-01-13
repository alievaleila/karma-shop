package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;
import az.edu.itbrains.karma_shop.model.Product;
import az.edu.itbrains.karma_shop.repository.ProductRepository;
import az.edu.itbrains.karma_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtoList = productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).toList();
        return productDtoList;
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        return productRepository.findTop6ByCategoryIdOrderByIdDesc(categoryId)
                .stream()
                .map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found " + id));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> filterTop6(Long categoryId, Long brandId, Long colorId, Double minPrice, Double maxPrice) {
        return productRepository.filterTop6(categoryId, brandId, colorId, minPrice, maxPrice)
                .stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }
}
