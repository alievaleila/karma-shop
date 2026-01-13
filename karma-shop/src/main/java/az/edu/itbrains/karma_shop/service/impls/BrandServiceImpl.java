package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.brand.BrandDto;
import az.edu.itbrains.karma_shop.repository.BrandRepository;
import az.edu.itbrains.karma_shop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BrandDto> getAll() {
        return brandRepository.findAll().stream().map(b -> modelMapper.map(b, BrandDto.class)).toList();
    }
}
