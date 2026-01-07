package az.edu.itbrains.karma_shop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
