package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.ProductDto;
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
}
