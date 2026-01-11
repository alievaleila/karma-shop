package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;
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
        return productRepository.findAllByCategoryId(categoryId).stream().map(p -> modelMapper
                .map(p, ProductDto.class)).toList();
    }
}
