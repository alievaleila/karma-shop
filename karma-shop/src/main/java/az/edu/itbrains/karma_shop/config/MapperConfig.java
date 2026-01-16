package az.edu.itbrains.karma_shop.config;

import az.edu.itbrains.karma_shop.dto.product.ProductDto;
import az.edu.itbrains.karma_shop.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        TypeMap<Product, ProductDto> typeMap = mapper.createTypeMap(Product.class, ProductDto.class);

        typeMap.addMappings(m -> {
            m.map(src -> src.getCategory().getName(), ProductDto::setCategoryName);
            m.map(src -> src.getBrand().getName(), ProductDto::setBrandName);
            m.map(src -> src.getColor().getName(), ProductDto::setColorName);
        });

        return mapper;
    }
}
