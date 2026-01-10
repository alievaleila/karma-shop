package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.category.CategoryDto;
import az.edu.itbrains.karma_shop.repository.CategoryRepository;
import az.edu.itbrains.karma_shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> mainCategories = categoryRepository.findAllByParentIsNull()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return mainCategories;
    }
}
